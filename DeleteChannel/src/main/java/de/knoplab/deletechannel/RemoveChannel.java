/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.deletechannel;

import io.scif.services.DatasetIOService;
import static java.lang.Math.toIntExact;
import java.lang.reflect.Array;
import java.util.Arrays;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.axis.CalibratedAxis;
import net.imagej.display.ImageDisplay;
import net.imagej.ops.OpService;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.event.EventHandler;
import org.scijava.io.event.DataOpenedEvent;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.NativeImgFactory;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.type.NativeType;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.integer.UnsignedIntType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.FloatType;

/**
 *
 * @author tuananh
 */
@Plugin(type = Command.class, menuPath = "Plugins>Remove Channel", label = "Test plugin")
public class RemoveChannel implements Command {

    @Parameter
    DatasetService datasetService;

    // we specify that the dataset is an input
    // ImageJ will inject the current dataset automatically
    @Parameter(type = ItemIO.BOTH)
    Dataset dataset;

    // The image display gives information about the view.
    // e.g. which plane is currently viewed etc.
    // ImageJ will inject the current image display
    @Parameter
    ImageDisplay currentDisplay;
    //

    @Parameter
    UIService uiService;

    /*
        This method is part of the plugin
     */
    public void run() {
        System.out.println("\nI'm running ! Here is the injected DatasetService : ");
        System.out.println(datasetService);

        System.out.println("\nAnd ImageJ takes care of setting the input for me !");
        System.out.println(dataset);

        // let's go through the different axes
        // first we create a array that contains informations about the axes of the image.
        CalibratedAxis[] axeArray = new CalibratedAxis[dataset.numDimensions()];
        // we fill it from the dataset
        dataset.axes(axeArray);

        for (CalibratedAxis axe : axeArray) {

            System.out.println(String.format("Type = %s, unit = %s", axe.type(), axe.unit()));
        }
        // Now let's go through the pixel of the image.
        // Let's say we want to go through the pixel of the current shown plane.
        // We must first know which plane is currently displayed.
        long[] position = new long[currentDisplay.numDimensions()];
        currentDisplay.localize(position);
        System.out.println(currentDisplay.numDimensions());
        // The display position always put X and Y to 0
        System.out.println("Position = " + Arrays.toString(position));

        RandomAccess<RealType<?>> randomAccess = dataset.randomAccess();
        dataset.axes(axeArray);
        long width = dataset.max(0);
        long height = dataset.max(1);

        System.out.println(String.format("Image %dx%d", width, height));

        DescriptiveStatistics stats = new DescriptiveStatistics();
        randomAccess.setPosition(position);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                position[0] = x;
                position[1] = 0;
                randomAccess.setPosition(x, 0);
                randomAccess.setPosition(y, 1);
                randomAccess.get();
                stats.addValue(randomAccess.get().getRealDouble());
            }
        }

        Dataset datasetDuplicate = dataset.duplicate();

        //dataset.setImgPlus(emptyImglus((Img<UnsignedIntType>) dataset.getImgPlus().getImg()));
        dataset = emptyDataset(dataset);
        setTypeAxis(datasetDuplicate, dataset);
        copyDataset(datasetDuplicate, dataset, 1);
        //dataset.setPlane(00, datasetDuplicate.getPlane(0));
        System.out.println("Done");
    }

    private < T extends Type< T>> ImgPlus emptyImglus(final Img< T> input) {
        CalibratedAxis[] axeArray = new CalibratedAxis[dataset.numDimensions()];
        dataset.axes(axeArray);

        int[] dim = new int[axeArray.length];
        for (int i = 0; i < dim.length; i++) {
            dim[i] = toIntExact(dataset.max(i) + 1);
            //To Change
            if (axeArray[i].type().toString().equals("Channel")) {
                dim[i] = dim[i] - 1;
            }
        }
        ImgFactory<T> imgFactory;
        imgFactory = (ImgFactory<T>) new CellImgFactory<>(5);

        Img< T> output = imgFactory.create(dim, input.firstElement());
        ImgPlus<T> imgPlus = new ImgPlus(output);

        return imgPlus;
    }

    private Dataset emptyDataset(Dataset input) {
        AxisType[] axisType = new AxisType[input.numDimensions()];
        CalibratedAxis[] axeArray = new CalibratedAxis[input.numDimensions()];
        input.axes(axeArray);

        long[] dims = new long[axeArray.length];
        for (int i = 0; i < dims.length; i++) {
            axisType[i] = axeArray[i].type();
            dims[i] = toIntExact(input.max(i) + 1);
            //To Change
            if (axeArray[i].type().toString().equals("Channel")) {
                dims[i] = dims[i] - 1;
            }
        }
        return datasetService.create(dims, dataset.getName(), axisType, input.getValidBits(), input.isSigned(), false);
    }

    private < T extends RealType< T>> void copyDataset(Dataset input, Dataset output, long toDelete) {
        Img<?> inputImg = input.getImgPlus().getImg();
        Cursor<T> cursorInput = (Cursor<T>) inputImg.localizingCursor();
        RandomAccess<T> randomAccess = (RandomAccess<T>) output.randomAccess();
        long[] position = null;
        position = new long[input.numDimensions()];
        long[] positionOutput = null;
        // iterate over the input cursor

        while (cursorInput.hasNext()) {
            // move input cursor forward
            cursorInput.fwd();
            cursorInput.localize(position);
            positionOutput = position;

            if (position[2] == toDelete) {
                continue;
            } else if (position[2] > toDelete) {
                positionOutput[2] = position[2] - 1;
            }
        output.setChannelMinimum((int) position[2],input.getChannelMinimum((int) position[2]));
        output.setChannelMaximum((int) position[2],input.getChannelMaximum((int) position[2]));

            randomAccess.setPosition(positionOutput);
            randomAccess.get().set(cursorInput.get());

        }
    }

    private void setTypeAxis(Dataset datasetTemplate, Dataset datasetToModified) {
        CalibratedAxis[] axeArrayToModified = new CalibratedAxis[datasetToModified.numDimensions()];
        datasetToModified.axes(axeArrayToModified);

        CalibratedAxis[] axeArrayTemplate = new CalibratedAxis[datasetTemplate.numDimensions()];
        datasetTemplate.axes(axeArrayTemplate);

        for (int i = 0; i < axeArrayTemplate.length; i++) {
            System.out.println(axeArrayTemplate[i].type().toString());
            if (i == 456)
            {
                axeArrayToModified[i].setType(Axes.get("Channel"));
            }
            else
            {
                axeArrayToModified[i].setType(axeArrayTemplate[i].type());
                axeArrayToModified[i].setUnit(axeArrayTemplate[i].unit());
            }
        }
        datasetToModified.setAxes(axeArrayTemplate);
    }

    public static class AutoOpener {

        @Parameter
        CommandService commandService;

        @Parameter
        DatasetIOService datasetIoService;

        @Parameter
        UIService uiService;
         

        @EventHandler
        public void onDatasetCreated(DataOpenedEvent event) {
            System.out.println("Damn !");
            commandService.run(RemoveChannel.class, true);
        }

        public void openExampleFile() throws Exception {
            Dataset dataset = datasetIoService.open("src/main/resources/16bit-multidim.tif");

            uiService.show(dataset);

        }
    }

}
