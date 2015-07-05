# mf2j

In-development Java parser for microformats2. All the hard work is done by [Jsoup](http://jsoup.org/).

Supports:
- basic property types
- implied properties
- rel-urls hash

TODO:
- value-class-pattern
- backwards compatibility

Live version: https://mf2j.herokuapp.com/?url=http://kylewm.com

Requirements: Java 1.5+

## Installation

```xml
<dependency>
  <groupId>com.kylewm</group>
  <artifactId>mf2j</artifactId>
  <version>0.0.4</artifact>
</dependency>
```

## Usage

```java
import com.kylewm.mf2j.Mf2Parser;
...

Mf2Parser parser = new Mf2Parser()
    .setIncludeAlternates(true)
    .setIncludeRelUrls(true);
Map<String,Object> parsed = parser.parse(new URI("https://kylewm.com"));
```
