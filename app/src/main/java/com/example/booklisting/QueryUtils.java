package com.example.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Books> fetchBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<Books> books = extractBooks(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of  objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Books> extractBooks(String json) {

        ArrayList<Books> books = new ArrayList<>();

        try {
            JSONObject strjson = new JSONObject(json);
            JSONArray ja = strjson.getJSONArray("items");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject item = ja.getJSONObject(i);
                JSONObject volInfo = item.getJSONObject("volumeInfo");

                String title;
                if (volInfo.has("title")) {
                    String title1 = volInfo.getString("title");
                    String stitle[] = title1.split(":");
                    title = stitle[0];
                } else {
                    continue;
                }
                String subTitle = "none";
                if (volInfo.has("subtitle")) {
                    subTitle = volInfo.getString("subtitle");
                }

                String publisher = "none";
                if (volInfo.has("publisher")) {
                    publisher = volInfo.getString("publisher");
                }
                String date = "none";
                if (volInfo.has("publishedDate")) {
                    date = volInfo.getString("publishedDate");
                }

                String description = "none";
                if (volInfo.has("description")) {
                    description = volInfo.getString("description");
                }

                String rating = "none";
                if (volInfo.has("averageRating")) {
                    double rate = volInfo.getDouble("averageRating");
                    rating = Double.toString(rate);
                }
                String ratingsCount = "none";
                if (volInfo.has("ratingsCount")) {
                    int ratingCount = volInfo.getInt("averageRating");
                    ratingsCount = Integer.toString(ratingCount);
                }
                String info_link;
                if (volInfo.has("previewLink")) {
                    info_link = volInfo.getString("previewLink");
                } else {
                    continue;
                }
                String img_link;
                if (volInfo.has("imageLinks")) {
                    JSONObject imagelinks = volInfo.getJSONObject("imageLinks");
                    String img_link1 = imagelinks.getString("thumbnail");
                    img_link = img_link1.replace("http", "https");
                } else {
                    continue;
                }
                String authors = "none";
                if (volInfo.has("authors")) {
                    String authorArray = volInfo.getJSONArray("authors").toString();
                    authors = authorArray.replaceAll("\"", "").replace("[", "").replace("]", "");
                }

                String categories = "none";
                if (volInfo.has("categories")) {
                    String categoriesArray = volInfo.getJSONArray("categories").toString();
                    categories = categoriesArray.replaceAll("\"", "").replace("[", "").replace("]", "");
                }

                JSONObject saleInfo = item.getJSONObject("saleInfo");
                String price = "none";
                if (saleInfo.has("retailPrice")) {
                    JSONObject retailprice = saleInfo.getJSONObject("retailPrice");
                    double p = retailprice.getDouble("amount");
                    price = Double.toString(p);
                }
                books.add(new Books(title, authors, img_link, info_link, rating, date, categories, price, subTitle, publisher, description, ratingsCount));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Books JSON results", e);
        }

        return books;
    }
}
