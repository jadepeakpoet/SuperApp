package com.singerstone.jojo

class 链表排序 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val head = Node(3, Node(1, Node(4, Node(2, Node(5, null)))))

            printLinkList(head)
            printLinkList(insertSort(head))
//            printLinkList(mergeSort(head))

        }

        /**
         * 归并排序
         * nlogn
         */
        private fun mergeSort(head: Node?): Node? {
            if (head?.next == null) {
                return head
            }
            var slow = head
            var fast = head
            var pre: Node? = null
            while (fast?.next != null) {
                pre = slow
                fast = fast.next?.next
                slow = slow?.next
            }
            pre?.next = null
            return mergeList(mergeSort(head), mergeSort(slow))
        }

        private fun mergeList(head1: Node?, head2: Node?): Node? {
            var head1 = head1
            var head2 = head2
            var dummy = Node(-1)
            var cur = dummy
            while (head1 != null && head2 != null) {
                if (head1.value ?: 0 < head2.value ?: 0) {
                    cur.next = head1
                    head1 = head1.next
                } else {
                    cur.next = head2
                    head2 = head2.next
                }
                cur = cur.next!!
            }
            if (head1 == null) cur.next = head2 else cur.next = head1
            return dummy.next

        }

        fun insertSort(head: Node?): Node? {
            var head = head
            var dummy = Node(-1)
            var cur: Node? = dummy
            while (head != null) {
                var tmp = head?.next
                cur = dummy
                while (cur?.next != null && cur?.next?.value ?: 0 <= head?.value ?: 0) {
                    cur = cur?.next
                }
                // head 插到cur后面
                head?.next = cur?.next
                cur?.next = head
                head = tmp
            }

            return dummy.next


        }

    }

}