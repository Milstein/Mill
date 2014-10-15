using System;
using Kurna.Models;
using ReactiveUI;
using System.Windows.Media;
using ReactiveUI.Xaml;

namespace Kurna.ViewModels
{
    public class GameViewModel : ReactiveObject
    {
        private static readonly Brush DefaultTileFill = Brushes.White;
        private Game game;

        public GameViewModel()
        {
            Game = new Game();
        }

        public Game Game
        {
            get { return game; }
            set { this.RaiseAndSetIfChanged(ref game, value); }
        }
    }
}
