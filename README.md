Scranular
=========

The start of my granular synthesizer programmed in scala. The language was chosen partly as a learning experience, and partly because I'm interested in the efficiency and dsp capabilities of the jvm but didn't feel like writing in java.

The input file is granulated and then played back.


Future plans include saving the output to a file, other grain types(FM,Osc). More input options and a gui.

To build make sure that your JAVA_HOME and SCALA_HOME environment variables are set. Then run ant from the root directory.

To run from command line:

Granulator <File Name>

Currently I have only tested with .wav files.

If you have any questions feel free to email lee.d.gauthier at gmail dot com.