package tp.example

class Picasso private constructor(val image: Int) {
    class Builder {
        var image: Int = 0
        fun build(): Picasso {
            return Picasso(image)
        }
    }
}

