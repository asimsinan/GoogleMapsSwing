# Embedding Google Maps in a JFrame using JFXPanel

I was looking for a solution to add Google Maps to a Swing-based project. I came across a paid solution (JxBrowser). I didn't want to pay for it to accomplish the tasks that I needed. So, I created my own solution for free.
This is a Netbeans project. The project uses JavaFX libraries to display Google Maps on a JFrame. You can access javascript functions from Java and map them to Java functions. All map related functions are inside GoogleMapsViewer class. It extends JFXPanel.
# Steps to run: #
1. Import the project from Netbeans.
2. Put your API Key in maps.html where it says "YOUR_API_KEY".
3. Run the GUI app.
4. Enter Center X, Center Y which are the beginning/center coordinates for generating random markers. Radius defines the boundries of generated markers.

# Supported Operations: #
1. Generate random unique coordinates.
2. Add random markers to Google Maps. 
3. Add info window, label to marker.
4. Delete marker.
5. Delete all markers.
6. Delete specific marker.
7. Center map and adjust zoom level.
8. Cluster markers.


![screenshot.gif](https://github.com/asimsinan/GoogleMapsSwing/blob/master/ss.gif)


