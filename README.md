# Assignment 0 - Echo Server

#### College: Northern Arizona University

#### Course: CS 465 Distributed Systems

#### Instructor: Wolf-Dieter Otte, Ph.D.

---
**What to do**

You are supposed to develop and code - in Java - a simple echo server. This server needs to live up to the following 
specs:

* The server is a multi-threaded server, which means that it can talk to any number of clients immediately. Note that, 
for this to work, it takes a specific design of the server loop.

* When the server receives input from a client's input stream, it will treat each byte in that stream as character data 
and sends it back, character-by-character, to the client immediately.

* We will have, however, a limitation on what kind of characters will be send back: any character received will only be 
sent back, if that character is a small or capital letter of the English alphabet.

* Upon reception of the word 'quit', the connection will be shut down and the thread talking to the client terminates. 
Note, however, that this does not mean that the server itself shuts down! I suggest to implement a simple/small state 
machine that keeps track of whether the prior input sequence, along with the current letter received, comprises the 
character sequence 'quit'.

* In addition to the plain word 'quit' for shutting down a connection, the character sequence 'quit' may be 
interspersed by any number of 'non-letter' characters, still having the effect of a shutdown of the connection to the 
client. Examples of shutdown sequences include: 'quit', 'q123u$#i66t', 'q0u0i0t', 'q u i t' etc. while these sequences 
DO NOT lead to a shutdown: 'qauaiat', 'quuit', 'q u ii t' etc.

* Have the server print out a control message whenever a client connected.

When implementing your server, I ask you to honor the following conventions. They are nonnegotiable, meaning that code 
not complying to these does not earn points:

* The server's class name is EchoServer

* The class name for the thread talking to the client is EchoThread. It needs to implement Runnable (and does not 
subclass Thread!)

* The variable holding a reference to the input stream pouring in characters from the client needs to be called 
fromClient

* The variable holding a reference to the output stream that is sending characters back to the client needs to be 
called toClient

* The variable that holds the character that was currently read from the client needs to be called charFromClient