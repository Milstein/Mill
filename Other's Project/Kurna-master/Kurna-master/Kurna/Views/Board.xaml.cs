using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Shapes;
using Kurna.Models;
using Kurna.ViewModels;
using System;
using System.Collections;
using System.Diagnostics;

namespace Kurna.Views
{
    /// <summary>
    /// Interaction logic for Board.xaml
    /// </summary>
    public partial class Board : UserControl
    {
        public Board()
        {
            InitializeComponent();
        }

        static Game game = ViewModelLocator.GameViewModel.Game;
        static PlayerViewModel players = ViewModelLocator.PlayerViewModel;

        enum SelectState
        {
            Neutral,
            PlaceNew,
            RemoveOpponentPiece,
            MoveExisting
        }

        private static SelectState currentState = SelectState.Neutral;

        private void Ellipse_MouseDown(object sender, MouseButtonEventArgs e)
        {
            var ellipse = sender as Ellipse;
            if (ellipse == null) return;
            var tile = game.Tiles.FirstOrDefault(t => ellipse.Tag as string == t.TileName);
            if (tile == null) return;

            if (currentState == SelectState.RemoveOpponentPiece)
            {
                if (players.PlayerOne.IsPlayersTurn && tile.Status == TileStatus.P2)
                {
                    tile.Status = TileStatus.Unoccupied;
                    players.PlayerTwo.PiecesLeft--;
                    if (players.PlayerTwo.PiecesLeft == 3 && players.PlayerTwo.InvisiblePieces == 0)
                        game.State = GameState.Flying;
                    if (players.PlayerTwo.PiecesLeft == 2 && players.PlayerTwo.InvisiblePieces == 0)
                    {
                        // game over logic
                        players.PlayerOne.IsPlayersTurn = false;
                        players.PlayerTwo.IsPlayersTurn = false;
                        game.Winner = players.PlayerOne.Name;
                        this.Content = new GameOverPage();
                    }
                    currentState = SelectState.Neutral;
                    players.SwitchTurns();
                }
                else if (players.PlayerTwo.IsPlayersTurn && tile.Status == TileStatus.P1)
                {
                    tile.Status = TileStatus.Unoccupied;
                    players.PlayerOne.PiecesLeft--;
                    if (players.PlayerOne.PiecesLeft == 3 && players.PlayerOne.InvisiblePieces == 0)
                        game.State = GameState.Flying;
                    if (players.PlayerOne.PiecesLeft == 2 && players.PlayerOne.InvisiblePieces == 0)
                    {
                        // game over logic
                        players.PlayerOne.IsPlayersTurn = false;
                        players.PlayerTwo.IsPlayersTurn = false;
                        game.Winner = players.PlayerTwo.Name;
                        this.Content = new GameOverPage();
                    }
                    currentState = SelectState.Neutral;
                    players.SwitchTurns();
                }
            }
            else if (game.State == GameState.Placing)
            {
                if (tile.Status != TileStatus.Unoccupied)
                {
                    // can't place on top of another piece 
                }
                else if (players.PlayerOne.IsPlayersTurn)
                {
                    tile.Status = TileStatus.P1;
                    players.PlayerOne.InvisiblePieces--;
                    players.PlayerOne.PiecesLeft++;
                    if (players.PlayerOne.AddNewMills(game.Tiles, TileStatus.P1, tile))
                    {
                        currentState = SelectState.RemoveOpponentPiece;
                    }
                    else players.SwitchTurns();
                }
                else if (players.PlayerTwo.IsPlayersTurn)
                {
                    tile.Status = TileStatus.P2;
                    players.PlayerTwo.InvisiblePieces--;
                    players.PlayerTwo.PiecesLeft++;
                    if (players.PlayerTwo.AddNewMills(game.Tiles, TileStatus.P2, tile))
                    {
                        currentState = SelectState.RemoveOpponentPiece;
                    }
                    else players.SwitchTurns();
                }
                if (players.PlayerOne.InvisiblePieces == 0 &&
                    players.PlayerTwo.InvisiblePieces == 0)
                {
                    game.State = GameState.Moving;
                }
            }
            else if (game.State == GameState.Moving)
            {
                if (game.CurrentlyMovingPiece == null)
                // This means that he hasn't selected a piece to move. Highlight the piece
                {
                    if (players.PlayerOne.IsPlayersTurn && tile.Status == TileStatus.P1 ||
                        players.PlayerTwo.IsPlayersTurn && tile.Status == TileStatus.P2)
                    {
                        game.CurrentlyMovingPiece = tile;
                        tile.Highlight();
                    }
                }
                else
                // This means that he has already selected a piece to move selected
                {
                    if (tile.Status == TileStatus.Unoccupied && game.CurrentlyMovingPiece.AdjacentTiles.Contains(tile))
                    {
                        tile.Status = game.CurrentlyMovingPiece.Status;
                        game.CurrentlyMovingPiece.Status = TileStatus.Unoccupied;
                        if (players.PlayerOne.IsPlayersTurn)
                        {
                            if (players.PlayerOne.AddNewMills(game.Tiles, TileStatus.P1, tile))
                            {
                                currentState = SelectState.RemoveOpponentPiece;
                            }
                            else players.SwitchTurns();
                        }
                        else if (players.PlayerTwo.IsPlayersTurn)
                        {
                            if (players.PlayerTwo.AddNewMills(game.Tiles, TileStatus.P2, tile))
                            {
                                currentState = SelectState.RemoveOpponentPiece;
                            }
                            else players.SwitchTurns();
                        }
                    }
                    game.CurrentlyMovingPiece.UnHighlight();
                    game.CurrentlyMovingPiece = null;
                }
            }
            else if (game.State == GameState.Flying)
            {
                if (game.CurrentlyMovingPiece == null)
                // This means that he hasn't selected a piece to move. Highlight the piece
                {
                    if (players.PlayerOne.IsPlayersTurn && tile.Status == TileStatus.P1 ||
                        players.PlayerTwo.IsPlayersTurn && tile.Status == TileStatus.P2)
                    {
                        game.CurrentlyMovingPiece = tile;
                        tile.Highlight();
                    }
                }
                else
                // This means that he has already selected a piece to move selected
                {
                    if (tile.Status == TileStatus.Unoccupied)
                    {
                        tile.Status = game.CurrentlyMovingPiece.Status;
                        game.CurrentlyMovingPiece.Status = TileStatus.Unoccupied;
                        if (players.PlayerOne.IsPlayersTurn)
                        {
                            if (players.PlayerOne.AddNewMills(game.Tiles, TileStatus.P1, tile))
                            {
                                currentState = SelectState.RemoveOpponentPiece;
                            }
                            else players.SwitchTurns();
                        }
                        else if (players.PlayerTwo.IsPlayersTurn)
                        {
                            if (players.PlayerTwo.AddNewMills(game.Tiles, TileStatus.P2, tile))
                            {
                                currentState = SelectState.RemoveOpponentPiece;
                            }
                            else players.SwitchTurns();
                        }
                    }
                    game.CurrentlyMovingPiece.UnHighlight();
                    game.CurrentlyMovingPiece = null;
                }
            }
            if (players.PlayerOne.IsPlayersTurn && players.PlayerOne.IsComputer)
            {
                PlayComputer();
            }
            else if(players.PlayerTwo.IsPlayersTurn && players.PlayerTwo.IsComputer)
            {
                PlayComputer();
            }
        }

        private void PlayComputer()
        {
            if (players.PlayerTwo.IsPlayersTurn && players.PlayerTwo.IsComputer)
            {

                System.Threading.Thread.Sleep(TimeSpan.FromMilliseconds(200));
                if (currentState == SelectState.RemoveOpponentPiece)
                {
                    #region RemoveOpponentPiece
                    var possibleTiles = game.Tiles.Where(x => x.Status == TileStatus.P1);
                    Random r = new Random();
                    var index = r.Next(possibleTiles.Count());
                    var tileA = possibleTiles.ToArray()[index];
                    var tile = game.Tiles.FirstOrDefault(x => x.TileName == tileA.TileName);
                    tile.Status = TileStatus.Unoccupied;
                    Debug.WriteLine("{0} removing tile {1}", players.PlayerTwo.Name, tile.TileName);
                    players.PlayerOne.PiecesLeft--;
                    if (players.PlayerOne.PiecesLeft == 3 && players.PlayerOne.InvisiblePieces == 0)
                        game.State = GameState.Flying;
                    if (players.PlayerOne.PiecesLeft == 2 && players.PlayerOne.InvisiblePieces == 0)
                    {
                        // game over logic
                        players.PlayerOne.IsPlayersTurn = false;
                        players.PlayerTwo.IsPlayersTurn = false;
                        game.Winner = players.PlayerTwo.Name;
                        this.Content = new GameOverPage();
                    }
                    currentState = SelectState.Neutral;
                    players.SwitchTurns();
                    #endregion
                }
                else if (game.State == GameState.Placing)
                {
                    #region Placing
                    var openTiles = game.Tiles.Where(x => x.Status == TileStatus.Unoccupied);
                    Random r = new Random();
                    int tileIndex = r.Next(openTiles.Count());
                    var tile = openTiles.ToArray()[tileIndex];
                    Debug.WriteLine("{0} {1}", players.PlayerTwo.Name, tile.TileName);
                    game.Tiles.FirstOrDefault(x => x.TileName == tile.TileName).Status = TileStatus.P2;
                    players.PlayerTwo.InvisiblePieces--;
                    players.PlayerTwo.PiecesLeft++;
                    if (players.PlayerTwo.AddNewMills(game.Tiles, TileStatus.P2, tile))
                    {
                        currentState = SelectState.RemoveOpponentPiece;
                    }
                    else
                    {
                        players.SwitchTurns();
                    }

                    if (players.PlayerOne.InvisiblePieces == 0 && players.PlayerTwo.InvisiblePieces == 0)
                    {
                        currentState = SelectState.Neutral;
                        game.State = GameState.Moving;
                    }
                    #endregion
                }
                else if (game.State == GameState.Moving || game.State == GameState.Flying)
                {
                    #region Moving
                    var myTiles = game.Tiles.Where(x => x.Status == TileStatus.P2);
                    var contprog = false;
                    Random r = new Random();
                    Tile newTile = null;
                    while (!contprog)
                    {
                        var index = r.Next(myTiles.Count());
                        var tile = myTiles.ToArray()[index];
                        var possible = tile.AdjacentTiles.ToArray();
                        for (int i = 0; i < possible.Count(); i++)
                        {
                            var current = game.Tiles.FirstOrDefault(x => x.TileName == possible[i].TileName);
                            if (current.Status == TileStatus.Unoccupied)
                            {
                                current.Status = TileStatus.P2;
                                game.Tiles.FirstOrDefault(x => x.TileName == tile.TileName).Status = TileStatus.Unoccupied;
                                newTile = current;
                                contprog = true;
                                break;
                            }
                        }
                    }
                    if (players.PlayerTwo.AddNewMills(game.Tiles, TileStatus.P2, newTile))
                    {
                        currentState = SelectState.RemoveOpponentPiece;
                    }
                    else
                    { 
                        players.SwitchTurns();
                    }
                    #endregion
                }
            }
            else if (players.PlayerTwo.IsPlayersTurn && players.PlayerTwo.IsComputer)
            {
                PlayComputer();
            }
        }

        private void MouseUpOnElipse(object sender, MouseButtonEventArgs e)
        {
            var ellipse = sender as Ellipse;
            if (ellipse == null) return;
            var tile = game.Tiles.FirstOrDefault(t => ellipse.Tag as string == t.TileName);
            if (tile == null) return;


            //currentlyMovingPiece = null;
        }

        private void MouseMovesOut(object sender, MouseEventArgs e)
        {

        }

        private void ellipse_MouseMove(object sender, MouseEventArgs e)
        {
            //var ellipse = sender as Ellipse;
            //if (ellipse != null && e.LeftButton == MouseButtonState.Pressed)
            //{
            //    DragDrop.DoDragDrop(ellipse, ellipse.Fill.ToString(), DragDropEffects.Move);
            //}
        }

        private void ellipse_GiveFeedback(object sender, GiveFeedbackEventArgs e)
        {
        }

        private void ellipse_DragEnter(object sender, DragEventArgs e)
        {
        }

        private void ellipse_DragLeave(object sender, DragEventArgs e)
        {
        }

        private void ellipse_DragOver(object sender, DragEventArgs e)
        {
        }

        private void ellipse_Drop(object sender, DragEventArgs e)
        {
        }
    }
}