/*
 * EmbeddedXOR.java
 *
 * Created on 7 maggio 2002, 19.27
 */

package org.joone.samples.engine.xor;

import org.joone.log.*;
import org.joone.engine.*;
import org.joone.net.NeuralNet;

import java.io.*;
/**
 * This example shows the use of a neural network embedded in another
 * application that gets the output from the DirectSynapse object
 * querying the neural network with one input pattern at time
 *
 * @author  pmarrone
 */
public class ImmediateEmbeddedXOR {
    /**
     * Logger
     * */
    private static final ILogger log = LoggerFactory.getLogger(ImmediateEmbeddedXOR.class);
    
    private double[][] inputArray = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
    private static String xorNet = "org/joone/samples/engine/xor/xor.snet";
    /** Creates a new instance of EmbeddedXOR */
    public ImmediateEmbeddedXOR() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            ImmediateEmbeddedXOR xor = new ImmediateEmbeddedXOR();
            xor.Go(xorNet);
    }
    
    private void Go(String fileName) {
        // We load the serialized XOR neural net
        NeuralNet xor = restoreNeuralNet(fileName);
        if (xor != null) {
            /* We get the first layer of the net (the input layer),
               then remove all the input synapses attached to it
               and attach a DirectSynapse */
            Layer input = xor.getInputLayer();
            input.removeAllInputs();
            DirectSynapse memInp = new DirectSynapse();
            input.addInputSynapse(memInp);
            
            /* We get the last layer of the net (the output layer),
               then remove all the output synapses attached to it
               and attach a DirectSynapse */
            Layer output = xor.getOutputLayer();
            output.removeAllOutputs();
            
            DirectSynapse memOut = new DirectSynapse();
            output.addOutputSynapse(memOut);
            // Now we interrogate the net
            xor.getMonitor().setLearning(false);
            xor.go();
            log.debug("Network running: "+xor.isRunning());
            /* ----------- Question: 
             * Where is the following code?
             * xor.getMonitor().setTotCicles(1);
             * xor.getMonitor().setPatterns(4);
             * ----------- Answer:
             * As the external application controls each run of the NN,
             * we don't need to invoke the above methods!
             */
            for (int n=0; n < 100; ++n) {
                log.debug("Launch #"+n);
                for (int i=0; i < 4; ++i) {
                    // Prepare the next input pattern
                    Pattern iPattern = new Pattern(inputArray[i]);
                    iPattern.setCount(i+1);
                    // Interrogate the net
                    memInp.fwdPut(iPattern);
                    // Read the output pattern and print out it
                    //double[] pattern = memOut.getNextPattern();
                    Pattern pattern = memOut.fwdGet();
                    log.debug("Output Pattern #"+(i+1)+" = "+pattern.getArray()[0]);
                }
            }
            // Injects a stop pattern to stop the network
            Pattern stop = new Pattern(new double[2]);
            stop.setCount(-1);
            memInp.fwdPut(stop);
            memOut.fwdGet();
            log.debug("Network running: "+xor.isRunning());
            log.debug("Finished");
        }
    }
    
    //
    // TO DO
    // add some comments
    private NeuralNet restoreNeuralNet(String fileName) {
        NeuralNet nnet = null;
        try {
            FileInputStream stream = new FileInputStream(fileName);
            ObjectInput input = new ObjectInputStream(stream);
            nnet = (NeuralNet)input.readObject();
        }
        catch (Exception e) {
            log.warn( "Exception thrown while restoring the Neural Net. Message is : " + e.getMessage(),
            e );
        }
        return nnet;
    }
    
}
