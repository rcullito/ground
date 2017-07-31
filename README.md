# n

n makes it simple to deal with conditional logic in the midst of threading expressions.

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.jpg"
 alt="Along the river" height="250" />

## Usage

Prefix forms within the `n` macro with another `n` to signify predicates that will return the form they were passed if true. 

If the form following `n` is falsey, then the macro borrows the behavior of `some->` : "short circuiting" out of the series of steps with a nil. 

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
