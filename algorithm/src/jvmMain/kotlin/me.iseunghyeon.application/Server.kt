package me.iseunghyeon.application

import java.util.Collections

fun main() {
    println(
        solution(
            40,
            arrayOf(
                intArrayOf(40, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
            )
        )
    )
    println(
        solution(
            5,
            arrayOf(
                intArrayOf(40, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
                intArrayOf(10, 10),
            )
        )
    )
    println(
        solution(
            100,
            arrayOf(
                intArrayOf(40, 20),
                intArrayOf(60, 56),
                intArrayOf(80, 10),
                intArrayOf(20, 10),
                intArrayOf(70, 18),
                intArrayOf(10, 4),
                intArrayOf(30, 17),
                intArrayOf(60, 46)
            )
        )
    )
    println(
        solution(
            80,
            arrayOf(
                intArrayOf(80, 20),
                intArrayOf(50, 40),
                intArrayOf(30, 10),
            )
        )
    )
    println(
        solution(
            80,
            arrayOf(
                intArrayOf(80, 10),
                intArrayOf(40, 10),
                intArrayOf(30, 10),
                intArrayOf(10, 10),
                intArrayOf(50, 10),
                intArrayOf(20, 10),
                intArrayOf(70, 10),
                intArrayOf(60, 10),
            )
        )
    )
    println(
        solution(
            80,
            arrayOf(
                intArrayOf(40, 10),
                intArrayOf(30, 10),
                intArrayOf(10, 10),
                intArrayOf(50, 10),
                intArrayOf(20, 10),
                intArrayOf(70, 10),
                intArrayOf(60, 10),
                intArrayOf(80, 20),
            )
        )
    )
}

fun solution(id_list: Array<String>, report: Array<String>, k: Int): IntArray {
    val reportMailCount = id_list.associateWith { 0 }.toMutableMap()
    report.toSet()
        .map { it.split(" ").run { get(0) to get(1) } }
        .let { reports ->
            val reported = reports.map { it.second }
            id_list
                .map { user ->
                    Collections.frequency(reported, user)
                        .takeIf { it >= k }
                        ?.run {
                            reports.filter { it.second == user }
                                .forEach { (key, _) ->
                                    reportMailCount[key] = (reportMailCount[key] ?: 0) + 1
                                }
                        }
                }
        }
    return reportMailCount.values.toIntArray()
}

var maxDepth = 0

fun solution(k: Int, dungeons: Array<IntArray>): Int {
    maxDepth = 0
    return search(dungeons, k)
}

fun search(
    dungeons: Array<IntArray>,
    _fatigue: Int,
): Int {
    var fatigue = _fatigue
    var depth = 0
    tailrec fun search(
        dungeons: Array<IntArray>,
        visited: MutableList<List<Int>>,
        nowVisit: MutableList<Int>,
    ): Int {
        if (maxDepth < depth) {
            maxDepth = depth
        }

        return when (dungeons.size) {
            depth -> depth
            else -> {
                val leftDungeonsIndex =
                    dungeons.toMutableList()
                        .apply {
                            ((visited[depth] + nowVisit).toSet())
                                .sortedDescending()
                                .forEach(this::removeAt)
                        }
                        .mapIndexed { index: Int, it: IntArray -> index to it }
                        .toMap()
                        .filter { it.value[0] <= fatigue }

                return if (leftDungeonsIndex.isEmpty()) {
                    if (depth == 0) maxDepth
                    else {
                        val nowDungeon = nowVisit.removeLast()
                        if (visited.size > depth + 1)
                            visited[depth + 1] = listOf()
                        fatigue += dungeons[nowDungeon][1]
                        depth--
                        search(dungeons, visited, nowVisit)
                    }
                } else {
                    val nextDungeon = dungeons.indexOf(leftDungeonsIndex.values.first())
                    if (visited.size > depth + 1)
                        visited[depth + 1] = listOf()
                    visited[depth] = visited[depth] + nextDungeon
                    nowVisit.add(nextDungeon)
                    fatigue -= dungeons[nextDungeon][1]
                    depth++
                    search(dungeons, visited, nowVisit)
                }
            }
        }
    }
    return search(dungeons, MutableList(dungeons.size) { listOf() }, mutableListOf())
}
