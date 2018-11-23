import entity.Entity


class Maze(private val entityGrid: Array<Array<Entity>>) {
    private val width = entityGrid[0].size
    private val height = entityGrid.size

}