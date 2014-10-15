module MorrisModel where
import Data.Bits
import Data.Word
import Data.Maybe

newtype Pos = Pos Int deriving (Eq, Ord, Show)
newtype Board = Board Word64 deriving (Eq, Ord, Show)
data Color = White | Black deriving (Eq, Show)

-- A blank board
blankBoard :: Board
blankBoard = Board 0

-- All positions on the board
positions :: [Pos]
positions = map Pos [0..23]

-- All possible mills on the board
-- 0        1        2
--    3     4     5
--       6  7  8
-- 9  10 11    12 13 14
--       15 16 17
--    18    19    20
-- 21       22       23
mills :: [[Pos]]
mills = map (map Pos) nums
    where nums = [[0,1,2],[3,4,5],[6,7,8], 			-- rows 1, 2, 3
                 [9,10,11],[12,13,14],				-- row 4
                 [15,16,17],[18,19,20],[21,22,23],	-- rows 5, 6, 7
                 [0,9,21],[3,10,18],[6,11,15],		-- cols 1, 2, 3
                 [1,4,7],[16,19,22],				-- col 4
                 [8,12,17],[5,13,20],[2,14,23]]		-- cols 5, 6, 7
				 
-- All valid edges between positions
edges :: [[Pos]]
edges = [[Pos x,Pos y]|[Pos x,Pos y,_] <- mills] ++ [[Pos x,Pos y]|[_,Pos x,Pos y] <- mills]

-- Updates the board with the correct value (white, black or empty) for pos
updateBoard :: Board -> Maybe Color -> Pos -> Board
updateBoard (Board bitBoard) c (Pos n) =
    Board $ newBits .|. (bitMask .&. bitBoard)
    where   index   = n * 2
            newBits	= shiftL (translate c) index
            bitMask	= complement $ shiftL 3 index
            translate (Just White)  = 2
            translate (Just Black)  = 1
            translate Nothing       = 0

-- Returns the state of the position
getPositionState :: Board -> Pos -> Maybe Color
getPositionState (Board bitBoard) (Pos n) =
    translate value
    where   index   = n * 2
            value   = 3 .&. shiftR bitBoard index
            translate 2	= Just White
            translate 1	= Just Black
            translate 0	= Nothing

-- Returns all positions on the board that has the state Color
getPositionsWithState :: Board -> Maybe Color -> [Pos]
getPositionsWithState b c =
    filter (\p -> c == getPositionState b p) positions

-- Returns whether or not the two positions have an edge between them
isAdjacent :: Pos -> Pos -> Bool
isAdjacent x y = elem [x,y] edges || elem [y,x] edges

-- Returns true if there is an edge between the positions AND the second position is not occupied
canMoveBetween :: Pos -> Pos -> Board -> Bool
canMoveBetween x y b = isAdjacent x y && isNothing (getPositionState b y)

-- Returns all the completed mills of the given color
getMills :: Board -> Color -> [[Pos]]
getMills b c = foldr (\m ms -> if millComplete m then m:ms else ms) [] mills
    where   positions    = getPositionsWithState b (Just c)
            millComplete = all (`elem` positions)

