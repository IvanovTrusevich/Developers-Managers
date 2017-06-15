package by.itransition.data.model;

public enum NewsType {
    FORUM_POST("news.project.forumPost"),
    NEW_PROJECT("news.project.new"),
    PROJECT_ARCHIVED("news.project.archive"),
    DEVELOPER_TO_PROJECT("news.project.developer"),
    DEVELOPER_TO_COMPANY("news.company.developer"),
    DEVELOPER_UPGRADE("news.company.upgrade"),
    DEVELOPER_FIRE("news.fire");

    private final String formatStringAdress;

    NewsType(String formatStringAdress) {
        this.formatStringAdress = formatStringAdress;
    }

    public String getFormatStringAddress() {
        return formatStringAdress;
    }
}
