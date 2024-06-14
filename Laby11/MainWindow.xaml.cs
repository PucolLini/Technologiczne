 using System;
using System.ComponentModel;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.IO;
using System.IO.Compression;

namespace Lab_4
{
    /// <summary>
    /// Logika interakcji dla klasy MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        delegate int CalculateNominatorDelegate(int N, int K);
        delegate int CalculateDenominatorDelegate(int K);

        private BackgroundWorker fibonacci_worker = new BackgroundWorker();

        public MainWindow()
        {
            InitializeComponent();
        }

        private async void OnButtonNewtonClick(object sender, RoutedEventArgs e)
        {
            int N = 7;
            int K = 2;

            //1.1 TASK
            Task<int> nominator_task = Task.Run(() => calculateNominator(N, K)); //licznik
            Task<int> denominator_task = Task.Run(() => calculateDenominator(K)); //mianownik

            Task.WaitAll(nominator_task, denominator_task);

            int result1 = nominator_task.Result / denominator_task.Result;

            Dispatcher.Invoke(() => TextBlockResult1.Text = $"Newton: {result1}");

            Console.WriteLine("Wynik1:");
            Console.WriteLine(result1);

            //1.2 DELEGATE

            CalculateNominatorDelegate nominator_delegate = new CalculateNominatorDelegate(calculateNominator);
            CalculateDenominatorDelegate denominator_delegate = new CalculateDenominatorDelegate(calculateDenominator);

            IAsyncResult nominator_result = nominator_delegate.BeginInvoke(N, K, null, null);
            IAsyncResult denominator_result = denominator_delegate.BeginInvoke(K, null, null);

            int result_nominator = nominator_delegate.EndInvoke(nominator_result);
            int result_denominator = denominator_delegate.EndInvoke(denominator_result);

            int result2 = result_nominator / result_denominator;

            Dispatcher.Invoke(() => TextBlockResult2.Text = $"Delegate: {result2}");

            Console.WriteLine("Wynik2:");
            Console.WriteLine(result2);

            //1.3 ASYNC
            int nominator = await calculateNominatorAsync(N, K);
            int denominator = await calculateDenominatorAsync(K);

            int result3 = nominator / denominator;

            Dispatcher.Invoke(() => TextBlockResult3.Text = $"Async: {result3}");

            Console.WriteLine("Wynik3:");
            Console.WriteLine(result3);
        }
        private static Task<int> calculateNominatorAsync(int N, int K)
        {
            return Task.Run(() =>
            {
                int result_nominator = 1;
                for (int i = N; i >= (N - K + 1); i--)
                {
                    result_nominator *= i;
                }
                return result_nominator;
            });
        }
        private static Task<int> calculateDenominatorAsync(int K)
        {
            return Task.Run(() =>
            {
                int result_denominator = 1;
                for (int i = 1; i <= K; i++)
                {
                    result_denominator *= i;
                }
                return result_denominator;
            });
        }
        private static int calculateNominator(int N, int K)
        {
            int result_nominator = 1;
            for (int i = N; i >= (N - K + 1); i--)
            {
                result_nominator *= i;
            }
            return result_nominator;
        }
        private static int calculateDenominator(int K)
        {
            int result_denominator = 1;
            for (int i = 1; i <= K; i++)
            {
                result_denominator *= i;
            }
            return result_denominator;
        }

        private async void OnButtonFibonacciClick(object sender, RoutedEventArgs e)
        {
            fibonacci_worker.WorkerReportsProgress = true;
            fibonacci_worker.DoWork += FibonacciWorker_DoWork;
            fibonacci_worker.ProgressChanged += FibonacciWorker_ProgressChanged;
            fibonacci_worker.RunWorkerCompleted += FibonacciWorker_RunWorkerCompleted;
            fibonacci_worker.RunWorkerAsync();
        }
        private void FibonacciWorker_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;

            int n = 17;

            int prev = 0;
            int current = 1;

            for (int i = 2; i <= n; i++)
            {
                int temp = prev + current;
                prev = current;
                current = temp;

                Thread.Sleep(15);

                int progress = (int)((double)i / n * 100);
                worker.ReportProgress(progress);
            }

            e.Result = current;
            Dispatcher.Invoke(() => TextBlockResult1.Text = $"Fibonacci: {current}");
        }
        private int CalculateFibonacci(int n, BackgroundWorker worker)
        {
            if (n <= 0) return 0;
            else if (n <= 1) return n;

            int prev = 0;
            int current = 1;

            for (int i = 2; i <= n; i++)
            {
                int temp = prev + current;
                prev = current;
                current = temp;

                Thread.Sleep(5);
                
                int progress = (int)((double)i / n * 100);
                worker.ReportProgress(progress);
            }
            return current;
        }
        private void FibonacciWorker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            ProgressFibonacci.Dispatcher.Invoke(() => {
                ProgressFibonacci.Value = e.ProgressPercentage;
            });
        }
        private void FibonacciWorker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            MessageBox.Show("Koniec obliczeń (fibonacci)");
        }

        private async void OnButtonCompressClick(object sender, RoutedEventArgs e)
        {
            var dialog = new Microsoft.Win32.OpenFileDialog
            {
                ValidateNames = false,
                CheckFileExists = false,
                CheckPathExists = true,
                FileName = "Wybierz katalog"
            };

            if (dialog.ShowDialog() == true)
            {
                string folder_path = System.IO.Path.GetDirectoryName(dialog.FileName);
                await CompressFilesAsync(folder_path);
                MessageBox.Show("Kompresja zakończona.");
            }
        }

        private async void OnButtonDecompressClick(object sender, RoutedEventArgs e)
        {
            var dialog = new Microsoft.Win32.OpenFileDialog
            {
                ValidateNames = false,
                CheckFileExists = false,
                CheckPathExists = true,
                FileName = "Wybierz katalog"
            };

            if (dialog.ShowDialog() == true)
            {
                string folder_path = System.IO.Path.GetDirectoryName(dialog.FileName);
                await DecompressFilesAsync(folder_path);
                MessageBox.Show("Dekompresja zakończona.");
            }
        }
        private async Task CompressFilesAsync(string folder_path)
        {
            string[] files = Directory.GetFiles(folder_path);

            foreach (string file in files)
            {
                await Task.Run(() =>
                {
                    using (FileStream source_stream = new FileStream(file, FileMode.Open))
                    {
                        using (FileStream target_stream = File.Create(file + ".gz"))
                        {
                            using (GZipStream compression_stream = new GZipStream(target_stream, CompressionMode.Compress))
                            {
                                source_stream.CopyTo(compression_stream);
                            }
                        }
                    }
                });
                Dispatcher.Invoke(() => TextBlockResult.Text = $"Skompresowano plik: {file}");
            }
        }

        private async Task DecompressFilesAsync(string folder_path)
        {
            string[] files = Directory.GetFiles(folder_path, "*.gz");

            foreach (string file in files)
            {
                await Task.Run(() =>
                {
                    string target_file_path = Path.Combine(Path.GetDirectoryName(file), Path.GetFileNameWithoutExtension(file));

                    using (FileStream source_stream = new FileStream(file, FileMode.Open))
                    {
                        using (FileStream target_stream = File.Create(target_file_path))
                        {
                            using (GZipStream decompression_stream = new GZipStream(source_stream, CompressionMode.Decompress))
                            {
                                decompression_stream.CopyTo(target_stream);
                            }
                        }
                    }
                });
                Dispatcher.Invoke(() => TextBlockResult.Text = $"Zdekompresowano plik: {file}");
            }
        }
    }
}
