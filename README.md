# Base64 Encode Decode
Base64 Encode Decode is a GUI application written in Java for encoding or decoding base64 text or file.

## Installation
For running the application, you can:
- Import the *src* folder in a new project in Eclipse, NetBeans or any other IDE for development in Java.
- Download the **Base64EncodeDecode.jar** file and run in a shell (cmd,bash...) the following command: 
`java -jar Base64EncodeDecode.jar`  
To run this command, you must have Java installed on your machine.

## Instructions
The application consists in a simple GUI with two text areas, one for the **plain text** and the other for the
**base64 text**; typing something in an area cause the translation in the other.
Moreover there are two buttons:
- **Encode File** for encoding a file in base64. Pressing this button will show an interface with a text area and two buttons: 
    * a **Open** button for choosing the file to encode; the result of the encoding will be shown in the text area. 
    * a **Save** button for saving the content of the text area in a file.
- **Decode File** for decoding a file in base64. Two dialogs will be shown one by one: 
    * the first for choosing the file to decode.
    * the second for saving the result of the decoding in a file (that has to be in the correct format).

## Author
Alessandro Chiariello (Demetrio).