namespace Kurna.ViewModels
{
    public class ViewModelLocator
    {
        private static GameViewModel gameViewModel;
        private static MainWindowViewModel mainWindowViewModel;

        public static PlayerViewModel playerViewModel;
        public static GameViewModel GameViewModel
        {
            get
            {
                if (gameViewModel == null)
                {
                    gameViewModel = new GameViewModel();
                }
                return gameViewModel;
            }
            set { gameViewModel = value; }
        }
        public static MainWindowViewModel MainWindowViewModel
        {
            get
            {
                if (mainWindowViewModel == null)
                {
                    mainWindowViewModel = new MainWindowViewModel();
                }
                return mainWindowViewModel;
            }
            set { mainWindowViewModel = value; }
        }
        public static PlayerViewModel PlayerViewModel
        {
            get
            {
                if (playerViewModel == null)
                {
                    playerViewModel = new PlayerViewModel();
                }
                return playerViewModel;
            }
            set { playerViewModel = value; }
        }
    }
}