using Kurna.Models;
using ReactiveUI;

namespace Kurna.ViewModels
{
    public class PlayerViewModel : ReactiveObject
    {
        public PlayerViewModel()
        {
            PlayerOne = new Player { IsPlayersTurn = true, Name = "Player 1" };
            PlayerTwo = new Player { IsPlayersTurn = false, Name = "Player 2" };
        }

        private Player playerOne;
        public Player PlayerOne
        {
            get { return playerOne; }
            set { this.RaiseAndSetIfChanged(ref playerOne, value); }
        }

        private Player playerTwo;
        public Player PlayerTwo
        {
            get { return playerTwo; }
            set { this.RaiseAndSetIfChanged(ref playerTwo, value); }
        }

        public void SwitchTurns()
        {
            if (PlayerOne.IsPlayersTurn)
            {
                PlayerOne.Turn++;
            }
            if (PlayerTwo.IsPlayersTurn)
            {
                PlayerTwo.Turn++;
            }
            PlayerOne.IsPlayersTurn = !PlayerOne.IsPlayersTurn;
            PlayerTwo.IsPlayersTurn = !PlayerTwo.IsPlayersTurn;
        }
    }
}
