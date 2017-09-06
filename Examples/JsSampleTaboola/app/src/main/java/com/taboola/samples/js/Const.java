package com.taboola.samples.js;

public class Const {

    public static final String BASE_URL = "http://example.com";

    public static final String CONTENT_HTML = "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <meta name='viewport' content='width=device-width, user-scalable=no' />\n" +
            "\n" +
            "    <script type=\"text/javascript\">\n" +
            "        !function (e, f, u, i) {\n" +
            "            if (!document.getElementById(i)){\n" +
            "                e.async = 1;\n" +
            "                e.src = u;\n" +
            "                e.id = i;\n" +
            "                f.parentNode.insertBefore(e, f);\n" +
            "            }\n" +
            "        }(document.createElement('script'),\n" +
            "            document.getElementsByTagName('script')[0],\n" +
            "            'https://cdn.taboola.com/libtrc/betterbytheminute-app/mobile-loader.js','tb-mobile-loader-script');\n" +
            "    </script>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1> first frame here!!</h1>\n" +
            "content by the publisher\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla bibendum mauris eget odio fermentum, non elementum lectus dapibus. Mauris egestas euismod orci molestie aliquet. Curabitur posuere, ante rhoncus sodales suscipit, sem tellus viverra erat, et venenatis augue quam non diam. In aliquam arcu eget nisl imperdiet finibus. Nunc pharetra sapien felis, vitae aliquam lorem bibendum in. Donec lacinia blandit tellus quis rutrum. Vivamus mattis mollis porta. Ut eleifend nisi quis magna faucibus ultricies.\n" +
            "</p>\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla bibendum mauris eget odio fermentum, non elementum lectus dapibus. Mauris egestas euismod orci molestie aliquet. Curabitur posuere, ante rhoncus sodales suscipit, sem tellus viverra erat, et venenatis augue quam non diam. In aliquam arcu eget nisl imperdiet finibus. Nunc pharetra sapien felis, vitae aliquam lorem bibendum in. Donec lacinia blandit tellus quis rutrum. Vivamus mattis mollis porta. Ut eleifend nisi quis magna faucibus ultricies.\n" +
            "</p>\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla bibendum mauris eget odio fermentum, non elementum lectus dapibus. Mauris egestas euismod orci molestie aliquet. Curabitur posuere, ante rhoncus sodales suscipit, sem tellus viverra erat, et venenatis augue quam non diam. In aliquam arcu eget nisl imperdiet finibus. Nunc pharetra sapien felis, vitae aliquam lorem bibendum in. Donec lacinia blandit tellus quis rutrum. Vivamus mattis mollis porta. Ut eleifend nisi quis magna faucibus ultricies.\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "    <a href=\"http://example.com\">some link on the publisher page</a>\n" +
            "</p>\n" +
            "<div id='taboola1'>\n" +
            "</div>\n" +
            "\n" +
            "<p>Nam euismod mauris id sapien mattis interdum. Curabitur ligula turpis, efficitur vitae est id, porttitor tempus diam. Phasellus nec lacus rhoncus mauris facilisis feugiat. Cras blandit dignissim facilisis. In blandit purus odio, sagittis porta neque rutrum vitae. Vestibulum consectetur, arcu et venenatis pulvinar, arcu lorem viverra nunc, commodo eleifend velit mauris eget eros. Proin pretium ex vel quam dapibus dictum.\n" +
            "</p>\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla bibendum mauris eget odio fermentum, non elementum lectus dapibus. Mauris egestas euismod orci molestie aliquet. Curabitur posuere, ante rhoncus sodales suscipit, sem tellus viverra erat, et venenatis augue quam non diam. In aliquam arcu eget nisl imperdiet finibus. Nunc pharetra sapien felis, vitae aliquam lorem bibendum in. Donec lacinia blandit tellus quis rutrum. Vivamus mattis mollis porta. Ut eleifend nisi quis magna faucibus ultricies.\n" +
            "</p>\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla bibendum mauris eget odio fermentum, non elementum lectus dapibus. Mauris egestas euismod orci molestie aliquet. Curabitur posuere, ante rhoncus sodales suscipit, sem tellus viverra erat, et venenatis augue quam non diam. In aliquam arcu eget nisl imperdiet finibus. Nunc pharetra sapien felis, vitae aliquam lorem bibendum in. Donec lacinia blandit tellus quis rutrum. Vivamus mattis mollis porta. Ut eleifend nisi quis magna faucibus ultricies.\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "    <a href=\"http://example.com\">some link on the publisher page</a>\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<div id='taboola2'>\n" +
            "</div>\n" +
            "\n" +
            "<script type='text/javascript'>\n" +
            "    window._taboola = window._taboola || [];\n" +
            "    _taboola[\"publisher\"] = \"betterbytheminute-app\";\n" +
            "\n" +
            "    _taboola.push({\n" +
            "        article: 'auto',\n" +
            "        url: ''\n" +
            "    });\n" +
            "\n" +
            "    _taboola.push({\n" +
            "        mode: 'thumbnails-sdk1',\n" +
            "        container: 'taboola1',\n" +
            "        placement: 'placement1-frame1',\n" +
            "        target_type: 'mix'\n" +
            "    });\n" +
            "\n" +
            "    _taboola.push({\n" +
            "        mode: 'thumbnails-sdk2',\n" +
            "        container: 'taboola2',\n" +
            "        placement: 'placement2-frame1',\n" +
            "        target_type: 'mix'\n" +
            "    });\n" +
            "\n" +
            "    _taboola[\"mobile\"] = [];\n" +
            "    _taboola[\"mobile\"].push({\n" +
            "\n" +
            "        // run sdkless when testing the js on a browser (no sdk) (optional)\n" +
            "        allow_sdkless_load:false,\n" +
            "\n" +
            "        // set view id in order to prevent duplicated between different placements (optional)\n" +
            "        taboola_view_id:\"hello-world\",\n" +
            "\n" +
            "        // override publisher from script url\n" +
            "        publisher:\"betterbytheminute-app\"\n" +
            "    });\n" +
            "\n" +
            "    _taboola.push({\n" +
            "        flush: true\n" +
            "    });\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>";

}
