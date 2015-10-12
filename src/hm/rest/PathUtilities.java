package hm.rest;

class PathUtilities {
    public static String removeExtraSlash(String path) {
        int indexOfLastSlash = path.lastIndexOf("/");
        if (path.length() > 1 && indexOfLastSlash == path.length() - 1)
            path = path.substring(0, indexOfLastSlash);
        return path;
    }
}
