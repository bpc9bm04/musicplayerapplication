export interface Track{
    id: string;
    name: string;    
    previewURL: string,    /* track preview url */
    albumName: string,
    artistName: string,
    href: string;          /* image path url*/
    playbackSeconds: number;    
    bookmarked: boolean;     /* flag to bookmark */  
}