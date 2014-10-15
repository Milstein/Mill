module MorrisTest where
import MorrisModel
import MorrisDefinitions
import Test.QuickCheck

instance Arbitrary Pos where
  arbitrary = oneof $ map return positions

instance Arbitrary Player where
  arbitrary = oneof [return $ Player White, return $ Player Black]

instance Arbitrary Board where
  arbitrary = return blankBoard
