/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.deletechannel;

import io.scif.services.DatasetIOService;
import static java.lang.Math.toIntExact;
import java.util.List;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.axis.CalibratedAxis;
import net.imagej.display.ImageDisplay;
import net.imagej.plugins.commands.display.AutoContrast;
import net.imagej.plugins.commands.display.interactive.BrightnessContrast;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.RealType;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.event.EventHandler;
import org.scijava.io.event.DataOpenedEvent;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import net.imglib2.img.Img;
import org.scijava.command.CommandInfo;

/**
 *
 * @author tuananh
 */
@Plugin(type = Command.class, menuPath = "Plugins>Remove Channel", label = "Test plugin")
public class RemoveChannel implements Command {

    @Parameter
    DatasetService datasetService;

    @Parameter(type = ItemIO.BOTH)
    Dataset dataset;

    @Parameter
    ImageDisplay currentDisplay;

    @Parameter
    UIService uiService;

    @Parameter
    CommandService commandService;

    @Parameter(label = "Channel")
    int channel;

    /*
        This method is part of the plugin
     */
    public void run() {

        try {
            deleteChannel();
        } catch (RemoveChannelException e) {
        }
    }

    private void deleteChannel() throws RemoveChannelException {
        if (channel > dataset.dimensionIndex(Axes.CHANNEL) || channel < 0) {
            throw new RemoveChannelException();
        }
        Dataset datasetDuplicate = dataset.duplicate();
        dataset = emptyDataset(dataset);
        copyDataset(datasetDuplicate, dataset, channel);
        commandService.run(AutoContrast.class, true);
        System.out.println("Delete channel done");
    }

    private Dataset emptyDataset(Dataset input) {
        AxisType[] axisType = new AxisType[input.numDimensions()];
        CalibratedAxis[] axeArray = new CalibratedAxis[input.numDimensions()];
        input.axes(axeArray);

        long[] dims = new long[axeArray.length];
        for (int i = 0; i < dims.length; i++) {
            axisType[i] = axeArray[i].type();
            dims[i] = toIntExact(input.max(i) + 1);
            if (axeArray[i].type().toString().equals("Channel")) {
                dims[i] = dims[i] - 1;
            }
        }
        return datasetService.create(dims, input.getName(), axisType, input.getValidBits(), input.isSigned(), false);
    }

    private < T extends RealType< T>> void copyDataset(Dataset input, Dataset output, long toDelete) {
        Img<?> inputImg = input.getImgPlus().getImg();
        Cursor<T> cursorInput = (Cursor<T>) inputImg.localizingCursor();
        RandomAccess<T> randomAccess = (RandomAccess<T>) output.randomAccess();
        long[] position = new long[input.numDimensions()];
        long[] positionOutput;

        while (cursorInput.hasNext()) {
            cursorInput.fwd();
            cursorInput.localize(position);
            positionOutput = position;

            if (position[2] == toDelete) {
                continue;
            } else if (position[2] > toDelete) {
                positionOutput[2] = position[2] - 1;
            }

            randomAccess.setPosition(positionOutput);
            randomAccess.get().set(cursorInput.get());

        }
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
            commandService.run(RemoveChannel.class, true);
        }

        public void openExampleFile() throws Exception {
            Dataset dataset = datasetIoService.open("src/main/resources/16bit-multidim.tif");

            uiService.show(dataset);

        }
    }

}
