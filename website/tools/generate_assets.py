#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Generates MyKey website + app assets from the REAL app sources:

1. mykey-site/assets/ndot-subset.ttf   - the app's default key font (Dot Matrix),
                                         subset to the glyphs a keyboard needs (~20KB)
2. avatar.png (site) + mykey_avatar.png (app drawable) - default pixel avatar
3. 8 kb-*.png gallery shots painted with the app's real keyboard design:
   same row widths as res/xml/row_qwerty*.xml, same dot-matrix icons as
   res/drawable/nth_*.xml, same colors as the Kotlin theme presets.

Run from the Keyboard root:  py mykey-site/tools/generate_assets.py
"""
import io
import os
import re
import colorsys

ROOT = os.path.normpath(os.path.join(os.path.dirname(__file__), "..", ".."))
SITE = os.path.join(ROOT, "mykey-site")
APP = os.path.join(ROOT, "Orbit-Board")

# ---------------------------------------------------------------- palette
# Colors lifted from the theme presets, keys derived with the app's own
# adjustColorBrightnessForContrast() formula (keyBorders = true).
THEMES = {
    "warm-white":    dict(bg="#FDF8F3", key="#FFFFFF", func="#F7EFE7", text="#211A16", accent="#B4552D", onAccent="#FFFFFF"),
    "classic-black": dict(bg="#000000", key="#0D0D0D", func="#1C1C1F", text="#E9E9EC", accent="#E8E8EA", onAccent="#121214"),
    "midnight-blue": dict(bg="#070D18", key="#0D182C", func="#1B2942", text="#E2E8F4", accent="#FF7A45", onAccent="#0B1220"),
    "forest":        dict(bg="#0B1410", key="#14241D", func="#22332A", text="#DDE7DC", accent="#7BD88F", onAccent="#00391B"),
    "sunset":        dict(bg="#1A0E0A", key="#2C1811", func="#3B2419", text="#F7E2D8", accent="#FF8A5B", onAccent="#4A1600"),
    "monochrome":    dict(bg="#0A0A0A", key="#171717", func="#262626", text="#EDEDED", accent="#EDEDED", onAccent="#1A1A1A"),
    "rose-quartz":   dict(bg="#FFF8FA", key="#FFFFFF", func="#F9EDF2", text="#1F1216", accent="#C46A8E", onAccent="#FFFFFF"),
    "terminal":      dict(bg="#040A06", key="#0B1C11", func="#15291C", text="#C8FFE0", accent="#3DFF9E", onAccent="#00210F"),
}
FILENAMES = {
    "warm-white": "kb-warm-white.png",
    "classic-black": "kb-classic-black.png",
    "midnight-blue": "kb-midnight-blue.png",
    "forest": "kb-forest.png",
    "sunset": "kb-sunset.png",
    "monochrome": "kb-monochrome.png",
    "rose-quartz": "kb-rose-quartz.png",
    "terminal": "kb-terminal.png",
}

# ---------------------------------------------------------------- icons
# Dot-matrix icons, extracted from res/drawable/nth_*.xml.
# Each entry: (cx, cy, r) in a 24x24 viewport.
ICON_SHIFT = [(18.0,15.0,4.133),(16.0,13.0,4.133),(14.0,11.0,4.133),(12.0,9.0,4.133),(10.0,11.0,4.133),(8.0,13.0,4.133),(6.0,15.0,4.133)]
ICON_SHIFTED = ICON_SHIFT + [(18.0,9.0,4.133),(16.0,7.0,4.133),(14.0,5.0,4.133),(12.0,3.0,4.133),(10.0,5.0,4.133),(8.0,7.0,4.133),(6.0,9.0,4.133)]
ICON_DELETE = [(21.0,4.0,1.944),(17.55,4.0,1.944),(14.101,4.0,1.944),(10.651,4.0,1.944),(7.474,4.601,1.944),(5.202,7.197,1.944),(2.931,9.793,1.944),(1.341,12.389,1.944),(3.612,14.986,1.944),(5.884,17.582,1.944),(8.236,20.0,1.944),(11.686,20.0,1.944),(15.136,20.0,1.944),(18.585,20.0,1.944),(21.988,19.736,1.944),(23.0,16.657,1.944),(23.0,13.207,1.944),(23.0,9.758,1.944),(23.0,6.308,1.944),(12.0,9.0,1.944),(13.5,10.5,1.944),(15.0,12.0,1.944),(16.5,13.5,1.944),(18.0,15.0,1.944),(18.0,9.0,1.944),(16.5,10.5,1.944),(13.5,13.5,1.944),(12.0,15.0,1.944)]
ICON_ENTER = [(9.0,10.0,1.0),(6.5,12.5,1.0),(4.0,15.0,1.0),(6.5,17.5,1.0),(9.0,20.0,1.0),(20.0,4.0,1.0),(20.0,7.61,1.0),(19.99,11.22,1.0),(18.3,14.27,1.0),(14.84,15.0,1.0),(11.22,15.0,1.0),(7.61,15.0,1.0)]
ICON_SMILE = [(9.0,9.0,1.0),(15.0,9.0,1.0),(12.005,2.0,1.0),(9.057,2.443,1.0),(6.373,3.737,1.0),(4.188,5.764,1.0),(2.698,8.344,1.0),(2.033,11.248,1.0),(2.256,14.219,1.0),(3.343,16.992,1.0),(5.199,19.322,1.0),(7.659,21.002,1.0),(10.505,21.882,1.0),(13.485,21.884,1.0),(16.332,21.007,1.0),(18.795,19.331,1.0),(20.656,17.003,1.0),(21.748,14.23,1.0),(21.963,11.258,1.0),(21.304,8.354,1.0),(19.817,5.773,1.0),(17.635,3.744,1.0),(14.952,2.448,1.0),(7.997,13.996,1.0),(9.778,15.44,1.0),(11.995,16.0,1.0),(14.213,15.445,1.0),(15.997,14.004,1.0)]

# row_qwerty4.xml: ?123 14% / emoji 10% / comma 9% / space 42% / period 10% / enter fillRight(15%)
# rows_qwerty row3: shift 15% / 7 keys 10% / delete 15%
ROW_SLOTS = [
    [("letter", ch) for ch in "qwertyuiop"],                                   # 10 x 10%
    [(None, ch) for ch in "asdfghjkl"],                                        # 9 x 10%, 5% indent
    [("shift", None)] + [(None, ch) for ch in "zxcvbnm"] + [("delete", None)], # 15+10x7+15
    [("symbol", None), ("emoji", None), ("comma", None), ("space", None),
     ("period", None), ("enter", None)],                                       # 14+10+9+42+10+15
]
ROW_WEIGHTS = [
    [10] * 10,
    [10] * 9,
    [15, 10, 10, 10, 10, 10, 10, 10, 10, 15],
    [14, 10, 9, 42, 10, 15],
]
ROW_INDENT = [0, 5, 0, 0]  # % of width before the first slot

HINTS = "1234567890"


def hex_rgba(h, alpha=255):
    h = h.lstrip("#")
    return (int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16), alpha)


def mix(hex_color, alpha):
    """color at `alpha` over the WarmWhite paper, approximating 50% text tints"""
    r, g, b, _ = hex_rgba(hex_color)
    return (r, g, b, alpha)


def subset_font():
    """Subset the app's ndot.ttf down to the glyphs a keyboard paints."""
    from fontTools import subset
    src = os.path.join(APP, "java", "res", "font", "ndot.ttf")
    dst = os.path.join(SITE, "assets", "ndot-subset.ttf")
    chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?!.,'\"-:;()@ "
    opts = subset.Options()
    opts.layout_features = []          # drop shaping tables, keyboard paints raw glyphs
    opts.name_IDs = [1, 2]
    opts.notdef_outline = True
    opts.recalc_bounds = False
    opts.drop_tables += ["GSUB", "GPOS", "GDEF"]
    font = subset.load_font(src, opts)
    sub = subset.Subsetter(options=opts)
    sub.populate(text=chars)
    sub.subset(font)
    font.save(dst)
    print("ndot-subset.ttf:", os.path.getsize(dst), "bytes")


def make_avatar():
    """Default avatar: 16x16 pixel person, upscaled. No letters."""
    from PIL import Image
    GRID = 16
    SCALE = 8
    bg = hex_rgba("#EFE4D6")
    ink = hex_rgba("#52443A")
    ink2 = hex_rgba("#84746A")
    accent = hex_rgba("#B4552D")

    img = Image.new("RGBA", (GRID, GRID), bg)
    px = img.load()

    def rect(x0, y0, x1, y1, c):
        for y in range(y0, y1 + 1):
            for x in range(x0, x1 + 1):
                px[x, y] = c

    # head (rounded pixel block)
    rect(6, 3, 9, 3, ink)
    rect(5, 4, 10, 7, ink)
    rect(6, 8, 9, 8, ink)
    # neck
    rect(7, 9, 8, 9, ink)
    # shoulders / chest
    rect(3, 11, 12, 14, ink)
    rect(2, 12, 13, 14, ink)
    rect(2, 13, 13, 14, ink)
    # accent band on the chest - a tiny MyKey touch
    rect(7, 12, 8, 12, accent)
    # corner shading pixels
    px[1, 1] = ink2; px[14, 1] = ink2
    px[1, 14] = ink2; px[14, 14] = ink2

    img = img.resize((GRID * SCALE, GRID * SCALE), Image.NEAREST)
    site_path = os.path.join(SITE, "assets", "avatar.png")
    img.save(site_path)
    print("avatar.png:", os.path.getsize(site_path), "bytes")

    app_dir = os.path.join(APP, "java", "res", "drawable-nodpi")
    os.makedirs(app_dir, exist_ok=True)
    img.save(os.path.join(app_dir, "mykey_avatar.png"))
    print("mykey_avatar.png written to app")


def draw_dots(draw, dots, box_x, box_y, box_size, color):
    """paint a dot-matrix icon (24x24 viewport) centered in box"""
    scale = box_size / 24.0
    ox = box_x + box_size / 2 - 12 * scale
    oy = box_y + box_size / 2 - 12 * scale
    r = dots[0][2] * scale
    for cx, cy, rad in dots:
        rr = rad * scale
        x, y = ox + cx * scale, oy + cy * scale
        draw.ellipse([x - rr, y - rr, x + rr, y + rr], fill=color)


def paint_keyboard(draw, W, H, theme, font, font_small, font_hint=None):
    """Paint the MyKey keyboard exactly like the app renders it."""
    t = THEMES[theme]
    C = {
        "bg": hex_rgba(t["bg"]),
        "key": hex_rgba(t["key"]),
        "func": hex_rgba(t["func"]),
        "text": hex_rgba(t["text"]),
        "dim": hex_rgba(t["text"], 128),
        "accent": hex_rgba(t["accent"]),
        "onAccent": hex_rgba(t["onAccent"]),
    }

    draw.rectangle([0, 0, W, H], fill=C["bg"])

    pad = int(W * 0.006)
    gap = int(W * 0.009)
    unit = W / 100.0

    strip_h = int(H * 0.17)
    # suggestions: center word is the typed/auto-correct candidate, sides are dimmer
    cy = strip_h // 2
    draw.text((W * 0.17, cy), "the", font=font_small, fill=C["dim"], anchor="mm")
    draw.text((W * 0.50, cy), "hello", font=font_small, fill=C["text"], anchor="mm")
    draw.text((W * 0.83, cy), "mykey", font=font_small, fill=C["dim"], anchor="mm")

    top = strip_h + int(H * 0.02)
    bottom_pad = int(H * 0.02)
    row_gap = int(H * 0.018)
    row_h = (H - top - bottom_pad - row_gap * 3) / 4.0
    radius = int(row_h * 0.16)
    icon_size = row_h * 0.52

    for r, slots in enumerate(ROW_SLOTS):
        weights = ROW_WEIGHTS[r]
        y = top + r * (row_h + row_gap)
        x = ROW_INDENT[r] * unit + pad
        hint_idx = 0
        for i, (kind, ch) in enumerate(slots):
            w = weights[i] * unit - gap
            is_letter = kind is None or kind == "letter"
            body = C["key"] if (is_letter or kind == "space") else C["func"]
            draw.rounded_rectangle([x, y, x + w, y + row_h], radius=radius, fill=body)

            mx, my = x + w / 2, y + row_h / 2
            if kind == "shift":
                draw_dots(draw, ICON_SHIFT, x + w / 2 - icon_size / 2, my - icon_size / 2, icon_size, C["text"])
            elif kind == "delete":
                draw_dots(draw, ICON_DELETE, x + w / 2 - icon_size / 2, my - icon_size / 2, icon_size, C["text"])
            elif kind == "enter":
                draw.rounded_rectangle([x, y, x + w, y + row_h], radius=int(row_h / 2), fill=C["accent"])
                draw_dots(draw, ICON_ENTER, x + w / 2 - icon_size * 0.8 / 2, my - icon_size * 0.8 / 2, icon_size * 0.8, C["onAccent"])
            elif kind == "emoji":
                draw_dots(draw, ICON_SMILE, x + w / 2 - icon_size * 0.9 / 2, my - icon_size * 0.9 / 2, icon_size * 0.9, C["text"])
            elif kind == "symbol":
                draw.text((mx, my), "?123", font=font_small, fill=C["text"], anchor="mm")
            elif kind == "comma":
                draw.text((mx, my), ",", font=font, fill=C["text"], anchor="mm")
            elif kind == "period":
                draw.text((mx, my), ".", font=font, fill=C["text"], anchor="mm")
            elif kind == "space":
                # the app shows the active language on the spacebar
                draw.text((mx, my), "English", font=font_small, fill=mix("#FFCC00", 210), anchor="mm")
            else:
                draw.text((mx, my), ch, font=font, fill=C["text"], anchor="mm")
                if r == 0 and hint_idx < len(HINTS):
                    hf = font_hint or font_small
                    draw.text((x + w - gap * 0.9, y + row_h * 0.16), HINTS[hint_idx],
                              font=hf, fill=mix(t["text"], 110), anchor="mm")
                hint_idx += 1
            x += weights[i] * unit


def regenerate_gallery():
    from PIL import Image, ImageDraw, ImageFont
    font_path = os.path.join(SITE, "assets", "ndot-subset.ttf")
    W, H = 900, 560
    f_big = ImageFont.truetype(font_path, 46)
    f_small = ImageFont.truetype(font_path, 30)
    f_hint = ImageFont.truetype(font_path, 22)
    for theme, fname in FILENAMES.items():
        img = Image.new("RGBA", (W, H))
        draw = ImageDraw.Draw(img)
        paint_keyboard(draw, W, H, theme, f_big, f_small, f_hint)
        out = os.path.join(SITE, "assets", fname)
        img.convert("RGB").save(out, optimize=True)
        print(fname, os.path.getsize(out), "bytes")


if __name__ == "__main__":
    subset_font()
    make_avatar()
    regenerate_gallery()
    print("done")
