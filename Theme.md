# App Template — Theming Guide

This repository has been converted into a generic Android app template. This document explains how to customize the app's theme and simple UI tokens used by the template.

## 1. Global Theme Tokens

Place global theme overrides in `app/src/main/res/values/themes.xml` or use Material tokens. Example tokens supported by this template:

- `primaryColor`, `secondaryColor`, `backgroundColor`
- `appDpi` (optional)

Use resource values for colors and dimensions to make themes swappable between flavors.

## 2. Styling and Assets

- Put drawable assets in `app/src/main/res/drawable`.
- Put default images in `app/src/main/res/mipmap-*/`.
- Use `styles.xml` and `themes.xml` to define light/dark variants.

## 3. Custom CSS / Web Assets (optional)

If your app uses a WebView, place static web files under `app/src/main/assets/www/`. Keep web UI independent of platform-specific logic.

## 4. Examples

Simple color resource example (res/values/colors.xml):

<pre>
&lt;resources&gt;
    &lt;color name="primaryColor"&gt;#6200EE&lt;/color&gt;
    &lt;color name="backgroundColor"&gt;#FFFFFF&lt;/color&gt;
&lt;/resources&gt;
</pre>

## 5. Next steps

- Edit `app/build.gradle.kts` to add/remove features and dependencies.
- Replace placeholder strings in `app/src/main/res/values/strings.xml` to set your app name and descriptions.
- Update `README.md` with your project-specific instructions.

This file replaces the previous WhatsApp-specific theming guide and contains generic guidance for using this project as a template.
