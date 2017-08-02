# n

like some->, except forms following n are treated as predicates that do not affect the value passed to the next form.

<img src="http://www.csstoday.com/UploadFiles/Multimedia/2015/4/201504161045388080.jpg"
 alt="Along the river" height="250" />

## Usage

Prefix forms within the `n` macro with another `n` to signify predicates that will return the form they were passed if true. 

If the form following `n` is falsey, then the macro borrows the behavior of `some->` : "short circuiting" out of the series of steps with a nil. 

## License

MIT
