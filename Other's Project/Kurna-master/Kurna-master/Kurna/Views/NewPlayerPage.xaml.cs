using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Kurna.Views
{
    /// <summary>
    /// Interaction logic for NewPlayerPage.xaml
    /// </summary>
    public partial class NewPlayerPage : UserControl
    {
        public NewPlayerPage()
        {
            InitializeComponent();
        }

        private void NextButtonClick(object sender, RoutedEventArgs e)
        {
            //TODO: Add validation checking
            var gamePage = new GamePage();
            this.Content = gamePage;
        }
    }
}
