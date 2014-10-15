-- Common types & definitions for the Nine Man Morris game.  
-- Supplied for Haskell programming project
-- Students should not change this module
-- CISC 260, Winter 2011
module MorrisDefinitions where
import MorrisModel
import Data.List

newtype Player = Player Color
  deriving (Eq, Show)

getPlayerMark :: Player -> Char
getPlayerMark (Player Black) = 'X'
getPlayerMark (Player White) = 'O'

opponent :: Player -> Player
opponent (Player Black) = Player White
opponent (Player White) = Player Black

invert :: Color -> Color
invert Black = White
invert White = Black

-- The state of a game at any time is described by a tuple of four items:
-- 1. person whose turn it is (humanChar or computerChar)
-- 2. the number of pieces the human player has not yet placed on the board 
-- 3. the number of pieces the computer has not yet placed on the board
-- 4. the board
type GameState = (Player, Int, Int, Board)

type Move = (Pos,Pos)

-- extract parts of a state
  
getPlayer :: GameState -> Player
getPlayer (p,_,_,_) = p

getPlayerColor :: Player -> Color
getPlayerColor (Player c) = c

getBoard :: GameState -> Board
getBoard (_,_,_,b) = b

getBlackCount :: GameState -> Int
getBlackCount (_,bpl,_,_) = bpl

getWhiteCount :: GameState -> Int
getWhiteCount (_,_,wpl,_) = wpl

switchPlayer :: GameState -> GameState
switchPlayer (p,bpl,wpl,b) = (opponent p,bpl,wpl,b)

