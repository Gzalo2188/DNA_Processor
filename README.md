<h1>DNA Processor</h1>
<body>

<p>This is a program that can count and search for single nucleotide mutations from a set of DNA Sequences. It allows for the import of text files containing the DNA sequences and populates a table where only the unique instances will be kept and counted.
In the mutations tab you can search for single nucleotide mutations.

Afterwards the sets can be exported in csv format. </p>

<h1>Build</h1>
<p>Under builds you can find the executables or jar files. The executable isnt signed so it might throw a warning on windows. If you're worried about the safety of the program then feel free to just build the project yourself.

If you want to build it yourself then make sure you have Eclipse, JavaFX, and Scene Builder.</p>

<h1>How to use</h1>
<p>Make sure all of the DNA is seperated on new lines. The program splits the text file based off new line characters. Afterwards it should automatically populate the table with each unique instance and count it.

For the mutations tab you need the previous table to be populated. You can copy one of the DNA's from the previous table and paste it in the search bar to populate the table with all the single nucleotide mutations for that specific DNA.</p>

<h1>Unique Instance Tab</h1>
<p>The inque instance tab will count all the unqieue instances of a DNA sequence and count them. You can use any of the column headers to sort the data.</p>

![DNA Unique instanace tab](assets/DNA_Picture_001.PNG)

<h1>Mutations Tab</h1>
<p>The mutations tab requires that a data set has been loaded. Once thats been done you can search a DNA Sequence. The mutations tab will only search for single nucleotide mutations(i.e if one character differs between the two strings.)</p>

![mutations tab](assets/DNA_Picture_002.PNG)

<h1>Unmatched Tab</h1>
<p>After searching in the mutations tab any DNA that wasnt matched gets displayed here.</p>

![Unmatched tab](assets/DNA_Picture_003.PNG)

<p>Each one of the tabs can have their information exported in CSV format.</p>
</body>

<h1>Known Issues</h1>
<p>Currently you can run into issues if the DNA sequences differ in length.</p>
