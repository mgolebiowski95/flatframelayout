# flatframelayout
FlatFrameLayout is writen based on https://github.com/hoang8f/android-flat-button ,but is a layout. You can put whatever you want in it.

## Installation

Library is installed by putting aar file into libs folder:

```
module/libs (ex. app/libs)
```

and adding the aar dependency to your `build.gradle` file:
```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.0-alpha05'
    implementation 'androidx.core:core-ktx:1.8.0'
    implementation "org.mini2Dx:gdx-math:1.9.13"
    implementation files("libs/flatframelayout-1.0.2.aar")
}
```

## Usage


### Attributes
| Attribute | Format |
|:---|:---:|
| FlatFrameLayout_color | color |
| FlatFrameLayout_cornersRadius | dimension |
| FlatFrameLayout_shadowColor | color |
| FlatFrameLayout_shadowHeight | dimension |
| FlatFrameLayout_borderColor | color |
| FlatFrameLayout_borderWidth | dimension |