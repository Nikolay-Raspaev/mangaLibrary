export default class MangaDto {
    constructor(args) {
        this.id = args.id || null;
        this.creatorId = args.creatorId || 0;
        this.mangaName = args.mangaName || "";
        this.image = args.image;
        this.chapterCount = args.chapterCount || 0;
    }
}