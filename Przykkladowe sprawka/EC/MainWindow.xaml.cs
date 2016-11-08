using RSA;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace EC
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void generateBtn_Click(object sender, RoutedEventArgs e)
        {
            var p = new BigInteger(aTextBox.Text, 10);
            var q = new BigInteger(bTextBox.Text, 10);
            var m = new BigInteger(mTextBox.Text, 10);
            try
            {
                var group = new ECGroup(p, q, m);
                ecGrouplistBox.ItemsSource = group.Generate();
                system = new ECCryptosystem(group);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

        private void determineGBtn_Click(object sender, RoutedEventArgs e)
        {
            ECPoint g = null;
            if ((g = ecGrouplistBox.SelectedItem as ECPoint) == null)
                return;
            var kGGroup = system.DeterminekG(g);
            kGGrouplistBox.ItemsSource = kGGroup;
        }

        private void exchangeBtn_Click(object sender, RoutedEventArgs e)
        {
            int na = int.Parse(naTextBox.Text);
            int nb = int.Parse(nbTextBox.Text);
            var k = system.KeyExchange(na, nb);

            outputBox.Text = string.Concat("Wymiana kluczy:\n\nG = ", system.G, "\nPa = ", system.Pa, "\nPb = ", system.Pb, "\nK = na*Pb = ", k[0], "\nK = nb*Pa = ", k[1]);
        }

        private void PmBtn_Click(object sender, RoutedEventArgs e)
        {
            ECPoint Pm = null;
            if ((Pm = ecGrouplistBox.SelectedItem as ECPoint) == null)
                return;
            PmTextBox.Text = Pm.ToString();
        }

        private void Cm1Btn_Click(object sender, RoutedEventArgs e)
        {
            ECPoint Cm1 = null;
            if ((Cm1 = ecGrouplistBox.SelectedItem as ECPoint) == null)
                return;
            Cm1TextBox.Text = Cm1.ToString();
        }

        private void Cm2Btn_Click(object sender, RoutedEventArgs e)
        {
            ECPoint Cm2 = null;
            if ((Cm2 = ecGrouplistBox.SelectedItem as ECPoint) == null)
                return;
            Cm2TextBox.Text = Cm2.ToString();
        }

        private void encipherBtn_Click(object sender, RoutedEventArgs e)
        {
            int nb = int.Parse(nbTextBox.Text);
            int k = int.Parse(kTextBox.Text);

            if (k >= system.C)
                return;

            var Cm = system.Encipher(new ECPoint(PmTextBox.Text), k, nb);
            outputBox.Text = string.Concat("Szyfrowanie:\n\n", "Cm1 = " + Cm[0] + '\n', "Cm2 = " + Cm[1]);
        }

        private void decipherBtn_Click(object sender, RoutedEventArgs e)
        {
            int nb = int.Parse(nbTextBox.Text);
            int k = int.Parse(kTextBox.Text);

            if (k >= system.C)
                return;

            var Pm = system.Decipher(new ECPoint(Cm1TextBox.Text), new ECPoint(Cm2TextBox.Text), k, nb);
            outputBox.Text = string.Concat("Deszyfrowanie:\n\n", "Pm = " + Pm);
        }

        private ECCryptosystem system = null;
    }
}
