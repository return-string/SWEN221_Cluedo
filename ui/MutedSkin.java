package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class MutedSkin implements CluedoSkin {
    final Font primaryTitleFont;
    final Font secondaryTitleFont;
    final Font normalFont;
    

    public MutedSkin() throws FontFormatException, IOException {
            primaryTitleFont =  Font.createFont(Font.PLAIN,
                                    new File("/bin/assets/fonts/IMFell_Normal.ttf"));
            secondaryTitleFont =  Font.createFont(Font.PLAIN,
                            new File("/bin/assets/fonts/PermanentMarker.ttf"));
            normalFont = Font.getFont("Arial");
    }

    @Override
    public Color cardBack() {
        return new Color(20,41,38);
    }

    @Override
    public Color cardFace() {
        return new Color(33,20,92);
    }

    @Override
    public Color primaryBG() {
        return Color.white;
    }

    @Override
    public Color secondaryBG() {
        return new Color(34,8,91);
    }

    @Override
    public Color tertiaryBG() {
        return new Color(33,34,86);
    }

    @Override
    public Color primaryText() {
        return new Color(33,32,22);
    }

    @Override
    public Color secondaryText() {
        return new Color(33,20,25);
    }

    @Override
    public Font titleFont() {
        return primaryTitleFont;
    }

    @Override
    public Font subtitleFont() {
        return secondaryTitleFont;
    }

    @Override
    public Font emphasisFont() {
        return normalFont;
    }

    @Override
    public Font normalFont() {
        return normalFont;
    }

    @Override
    public Font subtextFont() {
        return normalFont;
    }
}
