using System.Windows;
using System.Windows.Controls;

namespace Kurna.Views
{
    /// <summary>
    ///     Interaction logic for HomePage.xaml
    /// </summary>
    public partial class HomePage : UserControl
    {
        public HomePage()
        {
            InitializeComponent();
        }

        private void NewGame(object sender, RoutedEventArgs e)
        {
            var newPlayerPage = new NewPlayerPage();
            Content = newPlayerPage;
        }
    }
}
