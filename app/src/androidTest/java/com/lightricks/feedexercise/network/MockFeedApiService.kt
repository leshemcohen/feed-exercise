package com.lightricks.feedexercise.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 * todo: implement the mock feed API service here
 */
class MockFeedApiService: FeedApiService{
    val ASSET_BASE_PATH = "/Users/leshem/dev/feed-exercise/app/src/main/assets/"

    @Throws(IOException::class)
    fun readJsonFile(filename: String): String? {
        val br =
            BufferedReader(InputStreamReader(FileInputStream(ASSET_BASE_PATH + filename)))
        val sb = StringBuilder()
        var line: String = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }

    private fun readAsset(fileName: String): String {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

    override fun singleFeedItem(): Single<GetFeedResponse> {
        val json = readAsset("get_feed_response.json")
        //val json =readJsonFile("get_feed_response.json")
//        val json = "{\n" +
//                "  \"templatesMetadata\": [\n" +
//                "    {\n" +
//                "      \"configuration\": \"lensflare-unleash-the-power-of-nature.json\",\n" +
//                "      \"id\": \"01E18PGE1RYB3R9YF9HRXQ0ZSD\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01DJ4TM160ETZR0NT4HA2M0ZTK\",\n" +
//                "        \"01DJ4TM161MRR86QFAXJTWP7NM\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"lens-flare-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"UnleashThePowerOfNatureThumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"accountingtravis.json\",\n" +
//                "      \"id\": \"01DX1RB94P35Q1A2W6AA5XCQZ9\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01DJ4TM160ETZR0NT4HA2M0ZTK\",\n" +
//                "        \"01DJ4TM161P490A1DZ3AFKXNNF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"lightleaks-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"AccountingTravisThumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"yeti.json\",\n" +
//                "      \"id\": \"01EAEFVPZ6MFJEMCA8XB06HB01\",\n" +
//                "      \"isNew\": true,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01DJ4TM160ETZR0NT4HA2M0ZTK\",\n" +
//                "        \"01DJ4TM161P490A1DZ3AFKXNNF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"fashion-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"yeti-thumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"BusinessDev.json\",\n" +
//                "      \"id\": \"01DX1RB965Z96AD283559NJT9T\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01DJ4TM161P490A1DZ3AFKXNNF\",\n" +
//                "        \"01DJ4TM160ETZR0NT4HA2M0ZTK\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"holistic-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"BusinessDevDefaultThumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"start-living.json\",\n" +
//                "      \"id\": \"01EAEFVXVT6VS2R24GR7Q40XE0\",\n" +
//                "      \"isNew\": true,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01DJ4TM160ETZR0NT4HA2M0ZTK\",\n" +
//                "        \"01DJ4TM161NNZMQQEXSX6DE4QJ\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"fashion-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"start-living-thumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"beach4july.json\",\n" +
//                "      \"id\": \"01DX1RB94MBAKC6ECYGMKVJT4B\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": false,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01E2846M7HM3B12V0YTJM0M5KF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"fashion-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"Beach4JulyThumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"sliderevealboundingbox-happy-independence-day.json\",\n" +
//                "      \"id\": \"01EC34AY3EQTA5294ZRHC6FQMJ\",\n" +
//                "      \"isNew\": true,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01E2846M7HM3B12V0YTJM0M5KF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"slide-reveal-boundingbox-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"sliderevealboundingbox-happy-independence-day-thumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"recipes4july.json\",\n" +
//                "      \"id\": \"01DX1RB923YYSRNFQF90TJNP03\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01E2846M7HM3B12V0YTJM0M5KF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"fashion-template.json\",\n" +
//                "      \"templateThumbnailURI\": \"Recipes4JulyThumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"independence-day-celebration.json\",\n" +
//                "      \"id\": \"01EBX3ZC3HZEXJYKEQ8SZAMGDY\",\n" +
//                "      \"isNew\": true,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01E2846M7HM3B12V0YTJM0M5KF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"classic.json\",\n" +
//                "      \"templateThumbnailURI\": \"independence-day-celebration-thumbnail.jpg\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"configuration\": \"happy-valentine's-day.json\",\n" +
//                "      \"id\": \"01E7674TVS66G1AGGMDE3YJVD7\",\n" +
//                "      \"isNew\": false,\n" +
//                "      \"isPremium\": true,\n" +
//                "      \"templateCategories\": [\n" +
//                "        \"01E2846M7HM3B12V0YTJM0M5KF\"\n" +
//                "      ],\n" +
//                "      \"templateName\": \"classic.json\",\n" +
//                "      \"templateThumbnailURI\": \"happy-valentine's-day-thumbnail.jpg\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}\n"
        val getFeedResponse : GetFeedResponse
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ThumbnailUrlAdapter())
            .build()
        val adapter  = moshi.adapter(GetFeedResponse::class.java)
        getFeedResponse = adapter.fromJson(json)!!
        return Single.just(getFeedResponse);
    }

}
