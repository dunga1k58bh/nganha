package com.nganha.nganha.enums;

public enum GroupRole {
    OWNER(4),
    ADMIN(3),
    MODERATOR(2),
    MEMBER(1);

    private final int rank;

    GroupRole(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public boolean isHigherThan(GroupRole other) {
        return this.rank > other.rank;
    }

    public boolean isLowerThan(GroupRole other) {
        return this.rank < other.rank;
    }
}
