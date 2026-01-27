package com.thejohnsondev.domain.model

data class ArtworkSearchItem(
    val id: Int? = null,
    val title: String? = null,
    val thumbnail: ArtworkThumbnail? = null,
) {
    companion object {
        val demo1 = ArtworkSearchItem(
            id = 272694,
            title = "Divine Proportion",
            thumbnail = ArtworkThumbnail(
                lqip = "data:image/gif;base64,R0lGODlhBwAFAPUAAL+vlr+wmb2zo8CvlsGxmse1m8q4ns26oM27o8+9o82/rNC9o9nJsdrJsd3Ntd/NtN/Pt97PuN7Pud/Ru+HRuuDQu+DRu+LSveXVvOnWvN7TweDUwOfXwO/ex+/fyPHgx/DgyfLgyPXiygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAHAAUAAAYhwAHG0pA4AgeQxiGYLAydjUPBORQ+ngxIlABYLg2LhBAEADs=",
                width = 10376,
                height = 7355,
                altText = "A work made of letterpress and woodcut in black with hand lettering in red ink..."
            )
        )
        val demo2 = ArtworkSearchItem(
            id = 161,
            title = "Skyphos (Drinking Cup)",
            thumbnail = ArtworkThumbnail(
                lqip = "data:image/gif;base64,R0lGODlhBwAFAPUAABEREiAgISYmJycnKXd3eHt7fI2Nj4+PkZCQkpOSlJOTlaCgoqGho7W1uLa2ube3ubm5ur6+wcDAwcDAwsTDxMTDxsTExcTExsXFx8fGx8fHyMfHycjIycvLzMzLzc3Nz8/P0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAHAAUAAAYfQMgEg6FEHo/FIYFYPDCJAUBgwHBAhQDho+kUhxpPEAA7",
                width = 6407,
                height = 4805,
                altText = "A wide-mouthed drinking cup glazed in black with two handles extending from opposite sides."
            )
        )
    }
}