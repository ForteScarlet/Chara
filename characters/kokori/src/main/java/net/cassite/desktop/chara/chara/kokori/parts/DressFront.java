// ***LICENSE*** This file is licensed under GPLv2 with Classpath Exception. See LICENSE file under project root for more info

package net.cassite.desktop.chara.chara.kokori.parts;

import javafx.scene.Group;
import net.cassite.desktop.chara.chara.parts.AbstractPart;
import net.cassite.desktop.chara.graphic.Anima;
import net.cassite.desktop.chara.graphic.AnimaList;
import net.cassite.desktop.chara.util.Utils;

public class DressFront extends AbstractPart {
    private final AnimaList anima;

    public DressFront(Group parent) {
        super(parent);
        anima = new AnimaList(this::swap,
            new Anima(
                "animation/dress_front/dress_front_000.png",
                Utils.buildSeqNames("animation/dress_front/dress_front_", 0, 30, ".png")
            ),
            new Anima(
                "animation/dress_front/dress_front_030.png",
                Utils.buildSeqNames("animation/dress_front/dress_front_", 30, 40, ".png"))
                .setFps(Anima.DEFAULT_FPS / 2)
        );
    }

    private void swap(Anima a, Anima b) {
        if (a != null) {
            a.removeFrom(root);
        }
        b.addTo(root);
    }

    public void flutter() {
        anima.play();
    }
}
