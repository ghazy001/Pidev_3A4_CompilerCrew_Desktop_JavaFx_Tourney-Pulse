package services;

import javafx.scene.control.DateCell;

import java.time.LocalDate;
import java.util.List;

public class BlockedDateCell extends DateCell {

    private final List<LocalDate> blockedDates;

    public BlockedDateCell(List<LocalDate> blockedDates) {
        this.blockedDates = blockedDates;
    }

    @Override
    public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);

        if (empty || date == null) {
            setText(null);
            setStyle(null);
        } else {
            if (blockedDates.contains(date)) {
                setDisable(true);
                setStyle("-fx-background-color: red;"); // Optional visual cue for blocked dates
            } else {
                setDisable(false);
                setStyle(null); // Reset any custom styles
            }
        }
    }
}
