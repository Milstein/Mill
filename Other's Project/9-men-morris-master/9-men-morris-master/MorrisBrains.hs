-- The "brains" of the Nine Man Morris game.
-- This skeleton version contains "dummy" versions of
-- all of the functions that the main module expects
-- to use.  Students must fill in bodies for all the
-- functions required for the play level they're implementing.
-- You will need plenty of helper functions as well.
-- CISC 260, winter 2011
-- M. Lamb
module MorrisBrains where
import MorrisModel
import MorrisDefinitions
import Data.List

-- Returns the number of mills on the board for a given player
millCount :: Board -> Player -> Int
millCount b (Player c) = length $ getMills b c

-- Returns the number of twoOutOfThree:s on the board for the given player 
twoOutOfThreeCount :: Board -> Player -> Int
twoOutOfThreeCount b (Player c) = toots mills
  where 
    toots    = foldr (\m mc -> if isToot m then mc + 1 else mc) 0
    isToot m = mps m == 2 && not (any (`elem` ops) m)
    mps      = foldr (\p ps -> if p `elem` pps then ps + 1 else ps) 0
    ops      = getPositionsWithState b (Just $ invert c)
    pps      = getPositionsWithState b (Just c)

-- Returns the total board score of the current player. See code for details
playerScore :: Board -> Player -> Int
playerScore b p = mss + omss + toots + otoots
  where 
    mss    = 10 * millCount b p
    omss   = (-9) * millCount b (opponent p)
    toots  = 4 * twoOutOfThreeCount b p
    otoots = (-5) * twoOutOfThreeCount b (opponent p)

-------------------------------------------------------------------------------

-- Returns true if the first board yields a higher score than the second one
-- for the given player.
betterScore :: Player -> Board -> Board -> Bool
betterScore p nb ob = playerScore nb p > playerScore ob p
  
-- Returns true if the given move creates a mill for the given player
addsMill :: Board -> Player -> Move -> Bool
addsMill b p m = millCount b p > millCount (move b p m) p 

-- Returns true if the given move breaks one of the current players' mills
breaksMill :: Board -> Player -> Move -> Bool
breaksMill b p m = millCount b p < millCount (move b p m) p 

-------------------------------------------------------------------------------

-- Makes the given placement mote for the given player
place :: Board -> Player -> Pos -> Board
place b (Player c) = updateBoard b (Just c)

-- Makes the given move for the given player
move :: Board -> Player -> Move -> Board
move b (Player c) (f,t) = updateBoard (updateBoard b Nothing f) (Just c) t

-- Removes the piece at the given position from the board, currently not used
capture :: Board -> Pos -> Board
capture b = updateBoard b Nothing

-- REMOVE THE GAMESTATE DEPENDENT STUFF BELOW?

-- Adds a piece at the given position for the current player
addPiece :: GameState -> Pos -> GameState
addPiece (p,bpl,wpl,b) pos = newState $ getPlayerColor p
  where 
    newState Black = (p,bpl-1,wpl,place b p pos)
    newState White = (p,bpl,wpl-1,place b p pos)

-- Removes a piece from the board at the given position
removePiece :: GameState -> Pos -> GameState
removePiece (p,bpl,wpl,b) pos = (p,bpl,wpl,capture b pos)

-------------------------------------------------------------------------------

-- Returns the possible positions to move to from a given position
possibleDestinations :: Board -> Pos -> [Pos]
possibleDestinations b f = filter (\t -> canMoveBetween f t b) eps
  where eps = getPositionsWithState b Nothing
  
prop_possibleDestinations :: Board -> Pos -> Bool
prop_possibleDestinations b p = all (\pos -> canMoveBetween p pos b) $ possibleDestinations b p

-- Given a game state after a player has made a mill, returns a list of
-- the opponent pieces it would be legal to capture.  These are all the
-- pieces which are not part of a mill.  Exception: if there are no 
-- pieces outside a mill, then any piece may be captured.  
capturablePositions :: Board -> Player -> [Pos]
capturablePositions b (Player c) | not $ null capturable = sort capturable
                                 | otherwise             = sort ops
  where
    ops        = getPositionsWithState b (Just $ invert c)
    capturable = foldr (\m ps -> if hasMill m then deleteAll ps m else ps) ops mills
    deleteAll  = foldr delete
    hasMill    = all (`elem` ops)

-- Returns a list of all positions with movable pieces for the current player
movablePositions :: Board -> Player -> [Pos]
movablePositions b (Player c) = movable $ getPositionsWithState b (Just c)
  where movable = filter (not . null . possibleDestinations b)

prop_movablePositions :: Board -> Player -> Bool
prop_movablePositions b p | not (null mps) = any (\(o,d) -> canMoveBetween o d b) [ (o,d) | o <- mps, d <- positions ]
                          | otherwise      = True
  where mps = movablePositions b p

-- Returns a list of all possible moves for the current player
possibleMoves :: Board -> Player -> [Move]
possibleMoves b p = foldr (\from ms -> pms from ++ ms) [] $ movablePositions b p 
  where pms f = map (\t -> (f,t)) $ possibleDestinations b f

prop_possibleMoves :: Board -> Player -> Pos -> Bool
prop_possibleMoves b p pos = all (\(o,d) -> canMoveBetween o d b) $ possibleMoves b p

-------------------------------------------------------------------------------

data Status = BlackWon | WhiteWon | Ongoing
  deriving (Eq)

-- Returns the current state of the game
status :: GameState -> Status
status (p,bpl,wpl,b) | hasLost Black = WhiteWon 
                     | hasLost White = BlackWon
                     | otherwise     = Ongoing 
  where 
    hasLost c            = isMovePhase (p,bpl,wpl,b) && looseCondition p (pps c)
    looseCondition p' ps = length ps < 3 || not (canMove b p')
    pps c'               = getPositionsWithState b (Just c')

-- Returns true if the current player can make a move
canMove :: Board -> Player -> Bool
canMove b (Player c) = any (not . null . possibleDestinations b) pps
  where pps = getPositionsWithState b (Just c)

prop_canMove b (Player c) = all (\(o,d) -> not $ canMoveBetween o d b) [ (o,d) | o <- pps, d <- positions ]
  where pps = getPositionsWithState b (Just c)

isPlacingPhase :: GameState -> Bool
isPlacingPhase (Player Black,bpl,_,_) = bpl > 0
isPlacingPhase (Player White,_,wpl,_) = wpl > 0

isMovePhase :: GameState -> Bool
isMovePhase s = not $ isPlacingPhase s 

-------------------------------------------------------------------------------

-- Returns the "best" position for a placement move for the current player.
-- Assumes the game is not over, so there will be a legal move.
bestPlacement :: GameState -> Pos
bestPlacement (p,bpl,wpl,b) = best $ getPositionsWithState b Nothing
  where
    best         = foldr1 (\p' bp -> if better p' bp then p' else bp)
    better np op = betterScore p (place b p np) (place b p op)

prop_bestPlacement s = betterScore

-- Picks the best capture for the computer to make after a mill 
-- Parameters: starting state and list of possible captures (assume 
-- non-empty)
bestCapture :: GameState -> [Pos] -> Pos
bestCapture (p,_,_,b) = foldr1 (\c bc -> if better c bc then c else bc)
  where better nc oc = betterScore p (place b p nc) (place b p oc)
 
-- This function is like bestPlacement, but for phase 2 of the game
-- Given a game state (assuming it's the computer's turn), pick the best 
-- legal phase 2 move to make (moving a piece to an adjacent position).
-- Return value: the best move
-- Assumes the game is not [] over, so there will be a legal move.
-- Strategy:
--    A. If there's a move that gets you a mill (even if you have to 
--       break up a mill to do it), that's the best move
--    B. Move a piece away from a mill, hoping to move it back on your 
--       next move
--    C. Pick the move that gives you the state with the best score, as 
--       in phase 1.
bestMove :: GameState -> Move
bestMove (p,bpl,wpl,b) = foldr1 best $ possibleMoves b p
  where 
    best nm om | addsMill b p nm                            = nm
               | not (addsMill b p om) && breaksMill b p nm = nm
               | not (breaksMill b p om) && better nm om    = nm 
               | otherwise                                  = om
    better nm' om' = betterScore p (move b p nm') (move b p om')

  
