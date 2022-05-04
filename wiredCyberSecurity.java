// Importing JSoup libraries as well as IOException
package WebScraper.wiredCybersecurity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

// Class for web scraping and receiving all results from the
// Wired Security News page
public class wiredCyberSecurity {
    public static void main(String[] args)
    {
        // Creating string arrays for the Title, Link, and Topics, assuming there won't be more than 50 articles at a time
        // on the page.
        String[] links = new String[50];
        String[] articleTitles = new String[50];
        String[] topicList = new String[50];

        // Counters for looping arrays, and storyNumber counter to give what story number it is.
        int counter = 0;
        int linkCounter = 0;
        int storyNumber = 1;

        // Put in try block because of IOException that is thrown.
        try
        {
            // Formatting
            System.out.println();

            // Connecting to the wired cyber security URL, if it can't connect in 6 seconds it times out, otherwise it grabs the HTML from the URL.
            Document doc = Jsoup.connect("https://www.wired.com/tag/cybersecurity/").timeout(6000).get();

            // Grabbing the elements that are in the wrapper class, so as to gain access to each article's "box".
            Elements articleBoxes = doc.getElementsByClass("SummaryItemWrapper-gdEuvf bheJMz summary-item summary-item--has-border summary-item--article summary-item--no-icon summary-item--text-align-left summary-item--layout-placement-side-by-side-desktop-only summary-item--layout-position-image-left summary-item--layout-proportions-33-66 summary-item--side-by-side-align-center summary-item--standard SummaryItemWrapper-bGkJDw ifBcbu summary-list__item");

            // For every articleBox element...
            for(Element e : articleBoxes)
            {
               // Grabbing  the elements that are articleTitles and articleTopics
               Elements articleTitleElements = e.select("a.SummaryItemHedLink-cgPsOZ.cEGVhT.summary-item-tracking__hed-link.summary-item__hed-link");
               Elements articleTopicElements = e.select("a.RubricLink-CPHAg.fHSYRr.rubric__link");

                // After getting the topic elements, I go in, grab the topic, and add it to my topic array.
                // I had to do some data parsing to get the topic formatted properly.
                for(Element efg : articleTopicElements)
                {
                    String[] directorySplitUp = efg.absUrl("href").split("/");
                    directorySplitUp[4] = directorySplitUp[4].replace('-', ' ');
                    directorySplitUp[4] = directorySplitUp[4].toUpperCase();
                    topicList[linkCounter] = directorySplitUp[4];
                    linkCounter++;
                }

                // After getting the elements I go in, grab the title, and then add it to my titles array.
                // I then print out the Story number, the Topic, the Headline, and the direct URL to the article.
               for(Element ef : articleTitleElements)
               {
                   articleTitles[counter] = ef.getElementsByClass("SummaryItemHedBase-dZmlME liBqMC summary-item__hed").text();
                   System.out.printf("Wired Cybersecurity Story #%d \tIs about the topic: %s\nHeadline -> %s\nArticle Link -> %s\n\n", storyNumber, topicList[counter], articleTitles[counter], ef.absUrl("href"));
                   counter++;
                   storyNumber++;
               }

            }
            // Catching any IOExceptions that occur from using the connect method to get the URL.
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
}
