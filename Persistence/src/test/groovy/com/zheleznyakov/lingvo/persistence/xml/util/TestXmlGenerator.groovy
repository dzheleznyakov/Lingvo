package com.zheleznyakov.lingvo.persistence.xml.util

import java.util.stream.Collectors

class TestXmlGenerator {

    def methodMissing(String name, def args) {
        if (callForSimpleEntity(name, args))
            return simpleEntityHandler(name, args)

        if (callForCollectionEntity(name, args))
            return collectionEntityHandler(name, args)

        if (callForEmptyContainer(name, args))
            return emptyContainerHandler(name, args)

        null
    }

    private static boolean callForSimpleEntity(String name, def args) {
        name.endsWith('Entity') &&
                args.length <= 1 &&
                (args.length == 0 || args[0] == null ||args[0].class != Class)
    }

    private static boolean callForCollectionEntity(String name, def args) {
        name.endsWith('Entity') &&
                args.length >= 1 &&
                (args[0] == null || args[0] instanceof Class)
    }

    private static boolean callForEmptyContainer(String name, def args) {
        name.startsWith('empty') &&
                args.length == 1 &&
                args[0] instanceof Class
    }

    private static String simpleEntityHandler(String name, Object[] args) {
        String entityType = getEntityType(name)
        def value = args.length == 0 ? null : args[0]
        simpleEntity "${capitalise entityType}Entity", "${entityType}Value", value
    }

    private static String collectionEntityHandler(String name, Object[] args) {
        String entityType = getEntityType(name)
        int numberOfElements = args.length - 1
        Object[] elements = new Object[numberOfElements]
        System.arraycopy(args, 1, elements, 0, numberOfElements)
        collectionEntity "${capitalise entityType}Entity", "${entityType}Values", args[0], elements
    }

    private static String emptyContainerHandler(String name, Object[] args) {
        String containerType = (args[0] as Class).typeName
        String tag = name.drop('empty'.length())
        if (!tag)
            tag = (args[0] as Class).simpleName
        emptyContainer containerType, tag
    }

    private static String getEntityType(String name) {
        int entityTypeEndIndex = name.indexOf('Entity') - 1
        name[0..entityTypeEndIndex]
    }

    private static String capitalise(String s) {
        s[0].toUpperCase() + s.drop(1)
    }

    String mapEntity(Class mapClass, Object... entries) {
        def mapNode = "<myMap type='${mapClass.typeName}'>"
        for (int i = 0; i < entries.length; i += 2)
            mapNode += "<entry>${getEntryNode('key', entries[i])}${getEntryNode('value', entries[i + 1])}</entry>"
        mapNode += '</myMap>'
        "<MapEntity>$mapNode</MapEntity>"
    }

    private static String simpleEntity(String entityTag, String fieldTag, String value) {
        def valueNode = value == null ? '' : "<$fieldTag>$value</$fieldTag>"
        "<$entityTag>$valueNode</$entityTag>"
    }

    private static String collectionEntity(String entityTag, String fieldTag, Class collectionClass, Object... elements) {
        def collectionNode
        if (collectionClass) {
            collectionNode = "<$fieldTag type='${collectionClass.typeName}'>"
            for (def element : elements)
                collectionNode += getEntryNode('elem', element)
            collectionNode += "</$fieldTag>"
        } else
            collectionNode = ''
        "<$entityTag>$collectionNode</$entityTag>"
    }

    private static String emptyContainer(String containerType, String tag) {
        "<$tag type='$containerType' />"
    }

    private static String getEntryNode(String entryTag, IntegerEntity element) {
        def elementNode = "<integerValue>$element.intValue</integerValue>"
        getEntryNode(entryTag, element, elementNode)
    }

    private static String getEntryNode(String entryTag, DoubleEntity element) {
        def elementNode = "<doubleValue>$element.doubleValue</doubleValue>"
        getEntryNode(entryTag, element, elementNode)
    }

    private static String getEntryNode(String entryTag, BooleanEntity element) {
        def elementNode = "<booleanValue>$element.booleanValue</booleanValue>"
        getEntryNode(entryTag, element, elementNode)
    }

    private static String getEntryNode(String entryTag, List list) {
        def elementNode = list.stream().
                map { getEntryNode('elem', it) }.
                collect(Collectors.joining())
        getEntryNode(entryTag, list, elementNode)
    }

    private static String getEntryNode(String entryTag, element) {
        getEntryNode(entryTag, element, element)
    }

    private static String getEntryNode(String entryTag, element, elementNode) {
        "<$entryTag type='${element.getClass().typeName}'>${elementNode}</$entryTag>"
    }
}
