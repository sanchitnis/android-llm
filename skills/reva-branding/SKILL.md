---
name: reva-branding
description: Apply REVA University brand guidelines when creating ANY visual or document output for REVA University. Use this skill whenever the user mentions REVA, REVA University, wants branded content for a Bengaluru university, or asks for HTML artifacts, Instagram/social creatives, presentations (PPTX), Word documents (DOCX), dashboards, posters, banners, or any other creative output that must follow REVA brand standards. Trigger this skill even if the user doesn't say "branding" explicitly — if they say "make a page for REVA" or "create REVA slides", this skill applies. Always consult this skill before writing any CSS, choosing fonts, picking colours, placing logos, or setting social media dimensions for REVA content.
---

# REVA University Brand Guidelines

Consult this skill before producing any output — HTML, PPTX, DOCX, or social creatives — for REVA University. Follow every section relevant to the output type being created.

---

## 1. Brand Colours

| Name        | Hex       | RGB             | Use                                  |
|-------------|-----------|-----------------|--------------------------------------|
| REVA Orange | `#f7a35b` | 247, 163, 91    | Primary accent, CTAs, highlights     |
| REVA Grey   | `#4a4c55` | 74, 76, 85      | Dark backgrounds, secondary text     |
| White       | `#ffffff`  | 255, 255, 255   | Reversed text, light-on-dark layouts |

**Rules:**
- REVA Orange is always the primary accent. Never substitute with any other orange or gold.
- REVA Grey is used for dark panels, footers, and secondary text — not for primary headings.
- No other brand colours are permitted. Do not add blue, green, red, or purple accents.

---

## 2. Typography

| Font                  | Use Case              | Import Source                                               |
|-----------------------|-----------------------|-------------------------------------------------------------|
| **Plus Jakarta Sans** | Headings, UI labels   | `https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap` |
| **Glacial Indifference** | Body text, captions | Self-hosted or loaded via 1001fonts — use as fallback to `sans-serif` |

**Rules:**
- Always import Plus Jakarta Sans from Google Fonts for HTML outputs.
- Glacial Indifference is used for body/secondary text; fall back to `sans-serif` if unavailable.
- Never substitute Arial, Helvetica, Roboto, or any other system font for branded outputs.

---

## 3. Logo Usage

| Variant           | URL                                                                                 | When to Use                                      |
|-------------------|-------------------------------------------------------------------------------------|--------------------------------------------------|
| Primary Logo      | `https://upload.wikimedia.org/wikipedia/commons/5/5f/REVA_University_Bangalore.png` | Default — use on white or light backgrounds      |
| Reverse (White)   | Same URL, apply CSS `filter: brightness(0) invert(1)` to render in white            | Use on dark or REVA Orange backgrounds           |

**Rules:**
- Only two colour variants permitted: Primary or full-white Reverse.
- Do **not** recolour, rotate, distort, add drop shadows, or alter opacity.
- Do **not** place the logo on pink, dark grey, brown, or red backgrounds.
- Clear space: 2× logo height above and below; 4× logo height on left and right.
- In horizontal format, maintain 3.5× spacing between Srivatsa symbol and wordmark.

**NAAC A+ Logo:**
- Place in **top-left**; REVA logo in **top-right** on social creatives.
- Size NAAC logo so it fits within the height of the Srivatsa symbol — never larger.
- Same two-variant rule applies (Primary or full-white Reverse).

---

## 4. Output-Specific Rules

### 4A — HTML Artifacts & Web Pages

Always include this in `<head>`:

```html
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
```

CSS variables to declare at `:root` level:

```css
:root {
  --reva-orange: #f7a35b;
  --reva-grey:   #4a4c55;
  --reva-white:  #ffffff;
  --font-heading: 'Plus Jakarta Sans', sans-serif;
  --font-body:    'Glacial Indifference', sans-serif;
}
```

Standard usage patterns:

```css
/* Heading */
font-family: var(--font-heading);
color: var(--reva-grey);

/* Accent / CTA / highlight */
background-color: var(--reva-orange);
color: var(--reva-white);

/* Dark section */
background-color: var(--reva-grey);
color: var(--reva-white);
```

Logo embed (primary, light bg):
```html
<img src="https://upload.wikimedia.org/wikipedia/commons/5/5f/REVA_University_Bangalore.png"
     alt="REVA University" style="height:48px;" />
```

Logo embed (reverse, dark bg):
```html
<img src="https://upload.wikimedia.org/wikipedia/commons/5/5f/REVA_University_Bangalore.png"
     alt="REVA University" style="height:48px; filter: brightness(0) invert(1);" />
```

---

### 4B — Instagram / Social Media Creatives

| Format          | Dimensions     | Ratio |
|-----------------|----------------|-------|
| Feed Post       | 1080 × 1350 px | 4:5   |
| Reels / Stories | 1920 × 1080 px | 9:16  |

Layout rules:
- **Top-left**: NAAC A+ logo
- **Top-right**: REVA University logo
- REVA Orange and REVA Grey as dominant accent colours.
- Plus Jakarta Sans for headings; Glacial Indifference for body.
- No hyperlinks on the creative — use QR codes or direct to bio.

Required hashtags on every post:
```
#REVAuniversity #EducateToEnterprise
```

When producing HTML mockups of Instagram creatives, use a `1080×1350` canvas scaled with `transform: scale()` for preview, preserving exact pixel dimensions.

---

### 4C — Presentations (PPTX)

Read `/mnt/skills/public/pptx/SKILL.md` first for python-pptx mechanics, then apply:

- Slide background: white (`#ffffff`) or REVA Grey (`#4a4c55`) for dark slides.
- Title font: Plus Jakarta Sans Bold (approximate with Calibri Bold if embedding not possible; note the limitation).
- Body font: Glacial Indifference or Calibri as fallback.
- Accent colour for shapes, dividers, and highlights: REVA Orange (`#f7a35b`).
- Logo placement: top-right corner of title slide and section dividers. Use reverse (white) logo on grey/orange backgrounds.
- Footer: REVA Grey bar with white text using Plus Jakarta Sans, 10–11pt.
- No gradients unless explicitly requested. Flat colour only.

Hex → RGB conversions for python-pptx:
```python
from pptx.util import Pt
from pptx.dml.color import RGBColor

REVA_ORANGE = RGBColor(0xF7, 0xA3, 0x5B)
REVA_GREY   = RGBColor(0x4A, 0x4C, 0x55)
REVA_WHITE  = RGBColor(0xFF, 0xFF, 0xFF)
```

---

### 4D — Word Documents (DOCX)

Read `/mnt/skills/public/docx/SKILL.md` first for python-docx mechanics, then apply:

- Document heading colour: REVA Grey (`#4a4c55`) for H1/H2; REVA Orange (`#f7a35b`) for accent rules or H3 highlights.
- Body text: 11pt, dark grey (`#4a4c55`) or black.
- Font: Plus Jakarta Sans is not natively available in Word — use **Calibri** as the closest substitute, and note this in the document footer or a comment.
- Header: REVA Orange bar at top with white REVA logo (reverse variant) — implement as a table row with orange cell fill.
- Footer: page number + "REVA University | reva.edu.in" in Calibri 9pt, REVA Grey.
- Table accent: header row in REVA Orange with white text; alternating rows in light orange tint (`#fde8d4`) and white.

Hex → RGB for python-docx:
```python
from docx.shared import RGBColor

REVA_ORANGE = RGBColor(0xF7, 0xA3, 0x5B)
REVA_GREY   = RGBColor(0x4A, 0x4C, 0x55)
```

---

## 5. Brand Voice

Content tone: **curious, confident, entrepreneurial, practical** — not promotional or corporate-generic.

Always prioritise themes aligned with "Educate to Enterprise":
- Innovation & Research (patents, prototypes, hackathons)
- Entrepreneurship (REVA NEST, ventures, pitches)
- Industry Readiness (placements, internships, masterclasses)
- Interdisciplinary Learning (Design + Tech + Management)
- Leadership & Initiative (clubs, fests, competitions)
- Community Impact (sustainability, civic tech, volunteering)
- Student Spotlights ("I did this at REVA" stories)

---

## 6. Pre-Submission Checklist

Before finalising any REVA output, verify:

- [ ] Colours: `#f7a35b` (Orange) and/or `#4a4c55` (Grey) used correctly
- [ ] Fonts: Plus Jakarta Sans (headings) and Glacial Indifference (body) — or noted fallbacks
- [ ] Logo: Primary on light bg; Reverse (white) on dark/orange bg — no other variants
- [ ] Social creatives: NAAC top-left, REVA logo top-right; NAAC ≤ REVA logo height
- [ ] Clear space around logo respected (2× top/bottom, 4× sides)
- [ ] No hyperlinks on social creatives (QR code instead)
- [ ] Hashtags on social posts: `#REVAuniversity #EducateToEnterprise`
- [ ] Content tone matches "Educate to Enterprise" voice
- [ ] Output-type specific rules (4A/4B/4C/4D) applied correctly
