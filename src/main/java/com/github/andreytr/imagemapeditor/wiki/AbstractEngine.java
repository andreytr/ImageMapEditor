package com.github.andreytr.imagemapeditor.wiki;

import com.github.andreytr.imagemapeditor.model.MapItem;

import java.util.List;

/**
 * User: andreytr
 * Date: 27.03.2011
 * Time: 22:26:56
 */
abstract public class AbstractEngine {
    abstract public String getImageMapText(List<MapItem> mapItems);
}
