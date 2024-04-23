using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

[Serializable]
class FilesComparer : IComparer<string>
{
    public int Compare(string? x, string? y)
    {
        int lengthComparison = x.Length.CompareTo(y.Length);

        if (lengthComparison != 0)
        {
            return lengthComparison;
        }
        return String.Compare(x, y, StringComparison.Ordinal);
    }
}

static class Extensions
{
    public static void FindOldestElementDate(DirectoryInfo directory, ref DateTime oldestDate)
    {
        if (directory.CreationTime < oldestDate)
        {
            oldestDate = directory.CreationTime;
        }

        foreach (FileInfo file in directory.GetFiles())
        {
            if (file.CreationTime < oldestDate)
            {
                oldestDate = file.CreationTime;
            }
        }

        foreach (DirectoryInfo subdirectory in directory.GetDirectories())
        {
            FindOldestElementDate(subdirectory, ref oldestDate);
        }
    }

    public static string GetDosAttributes(FileSystemInfo info)
    {
        char[] attributes = { '-', '-', '-', '-' };

        attributes[0] = (info.Attributes & FileAttributes.ReadOnly) != 0 ? 'r' : '-';
        attributes[1] = (info.Attributes & FileAttributes.Archive) != 0 ? 'a' : '-';
        attributes[2] = (info.Attributes & FileAttributes.Hidden) != 0 ? 'h' : '-';
        attributes[3] = (info.Attributes & FileAttributes.System) != 0 ? 's' : '-';

        return new string(attributes);
    }
}

class Program
{
    static void Main(string[] args)
    {
        AppContext.SetSwitch("System.Runtime.Serialization.EnableUnsafeBinaryFormatterSerialization", true);

        if (args.Length == 0)
        {
            Console.WriteLine("Wpisz argument do wywołania programu.\n");
            return;
        }

        string directoryPath = args[0];

        if (!Directory.Exists(directoryPath))
        {
            Console.WriteLine("Ścieżka nie istnieje. \n");
            return;
        }

        Console.WriteLine("Ścieżka: " + directoryPath.ToString());

        SortedDictionary<string, long> directoryCollectionOfElements = new SortedDictionary<string, long>(new FilesComparer());

        //Drzewko
        ShowDirectoryContent(directoryPath, 1);

        //Najstarszy element
        DirectoryInfo directoryInfo = new DirectoryInfo(directoryPath);
        DateTime oldestFileTime = DateTime.MaxValue;

        Extensions.FindOldestElementDate(directoryInfo, ref oldestFileTime);

        Console.WriteLine();
        Console.WriteLine("Najstarszy plik: " + oldestFileTime.ToString());

        //Kolekcja
        var sortedCollection = SortDirectory(directoryInfo);

        // Serializacja i deserializacja
        //ShowCollection(sortedCollection);

        SerializeAndDeserializeCollection(sortedCollection);
    }

    static void SerializeAndDeserializeCollection(SortedDictionary<string, long> collection)
    {
        BinaryFormatter formatter = new BinaryFormatter();
        using (FileStream fileStream = new FileStream("collection.bin", FileMode.Create))
        {
            formatter.Serialize(fileStream, collection);
        }
        using (FileStream fileStream = new FileStream("collection.bin", FileMode.Open))
        {
            var deserializedCollection = (SortedDictionary<string, long>)formatter.Deserialize(fileStream);
            Console.WriteLine("\nDeserializowana kolekcja:");
            ShowCollection(deserializedCollection);
        }
    }
    static void ShowCollection(SortedDictionary<string, long> directoryElements)
    {
        foreach (var element in directoryElements)
        {
            Console.WriteLine(element.Key + " -> " + element.Value);
        }
    }

    static SortedDictionary<string, long> SortDirectory(DirectoryInfo directory)
    {
        var comparer = new FilesComparer();
        var sortedCollection = new SortedDictionary<string, long>(comparer);
        foreach (var file in directory.GetFiles())
        {
            sortedCollection.Add(file.Name, file.Length);
        }
        foreach (var subDirectory in directory.GetDirectories())
        {
            sortedCollection.Add(subDirectory.Name, subDirectory.GetFiles().Length);
        }
        return sortedCollection;
    }

    static void ShowDirectoryContent(string directoryPath, int nestingLevel)
    {
        string[] files = Directory.GetFiles(directoryPath);
        string[] directories = Directory.GetDirectories(directoryPath);

        foreach (string directory in directories)
        {
            string[] filesInDirectory = Directory.GetFiles(directory);
            string[] directoriesInDirectory = Directory.GetDirectories(directory);
            int numberOfElementsInDirectory = directoriesInDirectory.Length + filesInDirectory.Length;


            for (int i = 0; i < nestingLevel; i++)
            {
                Console.Write("  ");
            }

            FileInfo directoryInfo = new FileInfo(directory);

            Console.Write(Path.GetFileName(directory) + " (" + numberOfElementsInDirectory + ") " + Extensions.GetDosAttributes(directoryInfo));
            Console.WriteLine();

            //podkatalogi i podpliki
            ShowDirectoryContent(directory, nestingLevel + 1);
        }

        foreach (string file in files)
        {
            for (int i = 0; i < nestingLevel; i++)
            {
                Console.Write("  ");
            }

            FileInfo fileInfo = new FileInfo(file);

            Console.Write(fileInfo.Name + " " + fileInfo.Length + " bajtow " + Extensions.GetDosAttributes(fileInfo));
            Console.WriteLine();
        }
    }
}
