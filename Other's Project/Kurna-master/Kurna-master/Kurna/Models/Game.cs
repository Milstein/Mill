using ReactiveUI;

namespace Kurna.Models
{
    public class Game : ReactiveObject
    {
        private string winner = "";
        private GameState state;
        private ReactiveCollection<Tile> tiles;

        public Tile CurrentlyMovingPiece { get; set; }

        public Game()
        {
            state = GameState.Placing;
            CreateTiles();
        }

        public GameState State
        {
            get { return state; }
            set { this.RaiseAndSetIfChanged(ref state, value); }
        }

        public ReactiveCollection<Tile> Tiles
        {
            get { return tiles; }
            set { this.RaiseAndSetIfChanged(ref tiles, value); }
        }

        public string Winner
        {
            get { return winner; }
            set { this.RaiseAndSetIfChanged(ref winner, value);  }
        }

        protected void CreateTiles()
        {
            // Create the initial tiles
            Tiles = new ReactiveCollection<Tile>(
                new[]
                {
                    // Outer
                    new Tile { TileName = "OuterTopLeft", Row = 0, Column = 0 },
                    new Tile { TileName = "OuterTopMiddle", Row = 0, Column = 3 },
                    new Tile { TileName = "OuterTopRight", Row = 0, Column = 6 },
                    new Tile { TileName = "OuterMiddleLeft", Row = 3, Column = 0 },
                    new Tile { TileName = "OuterMiddleRight", Row = 3, Column = 6 },
                    new Tile { TileName = "OuterBottomLeft", Row = 6, Column = 0 },
                    new Tile { TileName = "OuterBottomMiddle", Row = 6, Column = 3 },
                    new Tile { TileName = "OuterBottomRight", Row = 6, Column = 6 },
                    // Middle
                    new Tile { TileName = "MiddleTopLeft", Row = 1, Column = 1 },
                    new Tile { TileName = "MiddleTopMiddle", Row = 1, Column = 3 },
                    new Tile { TileName = "MiddleTopRight", Row = 1, Column = 5 },
                    new Tile { TileName = "MiddleMiddleLeft", Row = 3, Column = 1 },
                    new Tile { TileName = "MiddleMiddleRight", Row = 3, Column = 5 },
                    new Tile { TileName = "MiddleBottomLeft", Row = 5, Column = 1 },
                    new Tile { TileName = "MiddleBottomMiddle", Row = 5, Column = 3 },
                    new Tile { TileName = "MiddleBottomRight", Row = 5, Column = 5 },
                    // Inner
                    new Tile { TileName = "InnerTopLeft", Row = 2, Column = 2 },
                    new Tile { TileName = "InnerTopMiddle", Row = 2, Column = 3 },
                    new Tile { TileName = "InnerTopRight", Row = 2, Column = 4 },
                    new Tile { TileName = "InnerMiddleLeft", Row = 3, Column = 2 },
                    new Tile { TileName = "InnerMiddleRight", Row = 3, Column = 4 },
                    new Tile { TileName = "InnerBottomLeft", Row = 4, Column = 2 },
                    new Tile { TileName = "InnerBottomMiddle", Row = 4, Column = 3 },
                    new Tile { TileName = "InnerBottomRight", Row = 4, Column = 4 }
                });

            // Make the connections

            // Outer
            Tiles[0].AdjacentTiles = new[] { Tiles[1], Tiles[3] };
            Tiles[1].AdjacentTiles = new[] { Tiles[0], Tiles[2], Tiles[9] };
            Tiles[2].AdjacentTiles = new[] { Tiles[1], Tiles[4] };
            Tiles[3].AdjacentTiles = new[] { Tiles[0], Tiles[5], Tiles[11] };
            Tiles[4].AdjacentTiles = new[] { Tiles[2], Tiles[7], Tiles[12] };
            Tiles[5].AdjacentTiles = new[] { Tiles[3], Tiles[6] };
            Tiles[6].AdjacentTiles = new[] { Tiles[5], Tiles[7], Tiles[14] };
            Tiles[7].AdjacentTiles = new[] { Tiles[4], Tiles[6] };
            // Middle
            Tiles[8].AdjacentTiles = new[] { Tiles[9], Tiles[11] };
            Tiles[9].AdjacentTiles = new[] { Tiles[1], Tiles[8], Tiles[10], Tiles[17] };
            Tiles[10].AdjacentTiles = new[] { Tiles[9], Tiles[12] };
            Tiles[11].AdjacentTiles = new[] { Tiles[3], Tiles[8], Tiles[13], Tiles[19] };
            Tiles[12].AdjacentTiles = new[] { Tiles[4], Tiles[10], Tiles[15], Tiles[20] };
            Tiles[13].AdjacentTiles = new[] { Tiles[11], Tiles[14] };
            Tiles[14].AdjacentTiles = new[] { Tiles[6], Tiles[13], Tiles[15], Tiles[22] };
            Tiles[15].AdjacentTiles = new[] { Tiles[12], Tiles[14] };
            // Outer
            Tiles[16].AdjacentTiles = new[] { Tiles[17], Tiles[19] };
            Tiles[17].AdjacentTiles = new[] { Tiles[9], Tiles[16], Tiles[18] };
            Tiles[18].AdjacentTiles = new[] { Tiles[17], Tiles[20] };
            Tiles[19].AdjacentTiles = new[] { Tiles[11], Tiles[16], Tiles[21] };
            Tiles[20].AdjacentTiles = new[] { Tiles[18], Tiles[12], Tiles[23] };
            Tiles[21].AdjacentTiles = new[] { Tiles[19], Tiles[22] };
            Tiles[22].AdjacentTiles = new[] { Tiles[14], Tiles[21], Tiles[23] };
            Tiles[23].AdjacentTiles = new[] { Tiles[20], Tiles[22] };

            this.RaisePropertyChanged(x => x.Tiles);
        }
    }
}