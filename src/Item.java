import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Item {
    private boolean isFound;
    private boolean isUsed;

    public void collect()
    {
        isFound = true;
    }

    public boolean getIsFound()
    {
        return isFound;
    }

    public void setIsUsed(boolean used)
    {
        isUsed = used;
    }

    public boolean getIsUsed()
    {
        return isUsed;
    }
}