VennPlot
========

This is a Java program for drawing rotationally symmetric Venn diagrams that are simple and monotone. 
Any closed curve divides the plane into two regions : the interior of the curve and the exterior of the curve. 
With a collection of n curves therefore, we can have up to 2<sup>n</sup> different regions. 
An n-Venn diagram is defined as a collection of n closed curves containing exactly 2<sup>n</sup> regions where each region is 
in the interior of a unique subset of the curves. Rotationally symmetric Venn diagrams exist only if the number of curves is prime [1].

Labelling the curves of an n-Venn diagram from 1 to n, we associate a binary number r to each region, called the rank of the region, 
where the i-th bit is 1 if curve number i contains the region, otherwise it is 0. The number of curves containing a region, 
which is the same as the number of 1's in the rank of the region, is called the weight of the region.

A Venn diagram is simple if no more than two curves intersect at any given point. A monotone Venn digram is the one in 
which any region of weight k is adjacent to at least one region of weight k + 1 and is also adjacent to at least one 
region of weight k-1.

A rotationally symmetric Venn diagram consists of n congruent pie slices. A pie slice of a simple symmetric monotone Venn 
diagram of n curves can be represented by a binary matrix of n-1 rows and (2<sup>n</sup> - 2)/n columns such that each 
column contains exactly one 1 and no two 1's are adjacent in a row. The 1's in the matrix represent the points at which 
the curves intersect. Similarly, it can be represented by a sequence of length (2<sup>n</sup> -2)/n of integers in 
range [0, n-2] which is called the crossing sequence. The i-th element indicates the row number of the matrix which 
contains a 1 at column i. 

For example the famous three circle Venn diagram can be represented by the following binary matrix and crossing sequence.

<p><blockcode><pre>
A pie-slice of symmetric 3-Venn diagram :<br> 
                            ____   ___________
                                \ /
                                 o
                            ____/ \____   ____
                                       \ /
                                        o
                            ___________/ \____
   
</pre></blockcode></p>
<p><blockcode><pre>
   Binary Matrix :              1      0          
                            
                                0       1
                                 
                             ---------------
                                 
   Crossing Sequence :          0   ,   1
</pre></blockcode></p>

The crossing sequence of some simple symmetric Venn diagram with different number of curves, including the first
simple syymetric 11-Venn diagram named Newroz [2], are available under directory InputData. 

Given the crossing sequence of a simple symmetric n-Venn diagram as input, this program creates an SVG file of the Venn diagram.

The program takes 5 arguments as input which are :
<ol>
<li>n : The number of curves</li>
<li>input-file-name : The name of the file containing the crossing sequences.
This file contains the crossing sequence of the Venn diagrams we want to draw.
Each diagram is given as a sequence of (2^n-2)/n integers in range [0, n-2] and separated by a space.</li>
<li>colours-file-name : The name of the file containing the codes of filling/stroke colours.
This files contains an integer n as the number of colours followed by n colour codes in hexadecimal format, for example, #FF0000 for the red colour.</li>
<li>filling-mode : Indicates the filling style. 1 : filled, 0 : not-filled.</li>
<li>drawing-mode : Indicates the style of drawing. 0 : Radial, 1 : Cylindrical.</li>
</ol>

Below are two example diagrams drawn by the program.

<img src="Diagrams/VD_ 5_Filled_01.png" width="200" height="200">
<img src="Diagrams/VD_ 7_Filled_19.png" width="200" height="200">

Running the program with the following arguments for eaxmple, draws all 23 simple symmetric monotone 7-Venn diagrams 
(See [3] for more information about generating Venn diagrams).
<br>
<code>7 VennSeven.txt SevenColors.txt 1 0<code>

<br>
Please feel free to contact me at Khalegh@gmail.com if you have any questions or comments about this code.

<h4> Refresences : <h4>

[1] Frank Ruskey and Mark Weston, <a href ="http://theory.cs.uvic.ca/~cos/venn/VennEJC.html"> <strong>A Survey of Venn Diagrams</strong></a>.
<br>
[2] K. Mamakani and F. Ruskey, <strong><a href="http://arxiv.org/abs/1207.6452">A New Rose : The First Simple Symmetric 11-Venn Diagram</a></strong>.
<br>
[3] K. Mamakani, W. Myrvold and F. Ruskey, <b>Generating Simple Convex Venn Diagrams</b>, Journal of Discrete Algorithms, 16 (2012) 270-286.
