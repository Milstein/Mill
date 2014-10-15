using System.Collections.Generic;
using System.Windows.Media;
using ReactiveUI;

namespace Kurna.Models
{
    public class Tile : ReactiveObject
    {
        public static readonly Brush UnoccupiedColor = Brushes.White;
        public static Brush P1Color
        {
            get { return Brushes.Aqua; }
        }
        public static Brush P2Color
        {
            get { return Brushes.BlueViolet; }
        }
        private Brush fillColor;
        private int strokeThickness;
        private TileStatus tileStatus;

        public Tile()
        {
            Status = TileStatus.Unoccupied;
            StrokeThickness = 0;
        }

        public TileStatus Status
        {
            get { return tileStatus; }
            set { OnTileStatusChanged(value); }
        }
        public Brush FillColor
        {
            get { return fillColor; }
            set { this.RaiseAndSetIfChanged(ref fillColor, value); }
        }
        public int StrokeThickness
        {
            get { return strokeThickness; }
            set { this.RaiseAndSetIfChanged(ref strokeThickness, value); }
        }
        public string TileName { get; set; }
        public int Row { get; set; }
        public int Column { get; set; }
        public IEnumerable<Tile> AdjacentTiles { get; set; }

        public void Highlight()
        {
            StrokeThickness = 5;
        }

        public void UnHighlight()
        {
            StrokeThickness = 0;
        }

        private void OnTileStatusChanged(TileStatus value)
        {
            if (value == TileStatus.Unoccupied)
                FillColor = UnoccupiedColor;
            if (value == TileStatus.P1)
                FillColor = P1Color;
            if (value == TileStatus.P2)
                FillColor = P2Color;
            this.RaiseAndSetIfChanged(ref tileStatus, value);
        }
    }
}