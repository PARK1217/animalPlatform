
let RES_SUCCESS = '0';

function X2JS(config) {
    'use strict';

    let VERSION = "1.1.4";

    config = config || {};
    initConfigDefaults();
    initRequiredPolyfills();

    function initConfigDefaults() {
        if(config.escapeMode === undefined) {
            config.escapeMode = true;
        }
        config.attributePrefix = config.attributePrefix || "_";
        config.arrayAccessForm = config.arrayAccessForm || "none";
        config.emptyNodeForm = config.emptyNodeForm || "text";
        if(config.enableToStringFunc === undefined) {
            config.enableToStringFunc = true;
        }
        config.arrayAccessFormPaths = config.arrayAccessFormPaths || [];
        if(config.skipEmptyTextNodesForObj === undefined) {
            config.skipEmptyTextNodesForObj = true;
        }
        if(config.stripWhitespaces === undefined) {
            config.stripWhitespaces = true;
        }
        config.datetimeAccessFormPaths = config.datetimeAccessFormPaths || [];
    }

    let DOMNodeTypes = {
        ELEMENT_NODE       : 1,
        TEXT_NODE          : 3,
        CDATA_SECTION_NODE : 4,
        COMMENT_NODE       : 8,
        DOCUMENT_NODE      : 9
    };

    function initRequiredPolyfills() {
        function pad(number) {
            let r = String(number);
            if ( r.length === 1 ) {
                r = '0' + r;
            }
            return r;
        }
        // Hello IE8-
        if(typeof String.prototype.trim !== 'function') {
            String.prototype.trim = function() {
                return this.replace(/^\s+|^\n+|(\s|\n)+$/g, '');
            }
        }
        if(typeof Date.prototype.toISOString !== 'function') {
            // Implementation from http://stackoverflow.com/questions/2573521/how-do-i-output-an-iso-8601-formatted-string-in-javascript
            Date.prototype.toISOString = function() {
                return this.getUTCFullYear()
                    + '-' + pad( this.getUTCMonth() + 1 )
                    + '-' + pad( this.getUTCDate() )
                    + 'T' + pad( this.getUTCHours() )
                    + ':' + pad( this.getUTCMinutes() )
                    + ':' + pad( this.getUTCSeconds() )
                    + '.' + String( (this.getUTCMilliseconds()/1000).toFixed(3) ).slice( 2, 5 )
                    + 'Z';
            };
        }
    }

    function getNodeLocalName( node ) {
        let nodeLocalName = node.localName;
        if(nodeLocalName == null) // Yeah, this is IE!!
            nodeLocalName = node.baseName;
        if(nodeLocalName == null || nodeLocalName=="") // =="" is IE too
            nodeLocalName = node.nodeName;
        return nodeLocalName;
    }

    function getNodePrefix(node) {
        return node.prefix;
    }

    function escapeXmlChars(str) {
        if(typeof(str) == "string")
            return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#x27;').replace(/\//g, '&#x2F;');
        else
            return str;
    }

    function unescapeXmlChars(str) {
        return str.replace(/&amp;/g, '&').replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&quot;/g, '"').replace(/&#x27;/g, "'").replace(/&#x2F;/g, '\/');
    }

    function toArrayAccessForm(obj, childName, path) {
        switch(config.arrayAccessForm) {
            case "property":
                if(!(obj[childName] instanceof Array))
                    obj[childName+"_asArray"] = [obj[childName]];
                else
                    obj[childName+"_asArray"] = obj[childName];
                break;
            /*case "none":
            break;*/
        }

        if(!(obj[childName] instanceof Array) && config.arrayAccessFormPaths.length > 0) {
            let idx = 0;
            for(; idx < config.arrayAccessFormPaths.length; idx++) {
                let arrayPath = config.arrayAccessFormPaths[idx];
                if( typeof arrayPath === "string" ) {
                    if(arrayPath == path)
                        break;
                }
                else
                if( arrayPath instanceof RegExp) {
                    if(arrayPath.test(path))
                        break;
                }
                else
                if( typeof arrayPath === "function") {
                    if(arrayPath(obj, childName, path))
                        break;
                }
            }
            if(idx!=config.arrayAccessFormPaths.length) {
                obj[childName] = [obj[childName]];
            }
        }
    }

    function fromXmlDateTime(prop) {
        // Implementation based up on http://stackoverflow.com/questions/8178598/xml-datetime-to-javascript-date-object
        // Improved to support full spec and optional parts
        let bits = prop.split(/[-T:+Z]/g);

        let d = new Date(bits[0], bits[1]-1, bits[2]);
        let secondBits = bits[5].split("\.");
        d.setHours(bits[3], bits[4], secondBits[0]);
        if(secondBits.length>1)
            d.setMilliseconds(secondBits[1]);

        // Get supplied time zone offset in minutes
        if(bits[6] && bits[7]) {
            let offsetMinutes = bits[6] * 60 + Number(bits[7]);
            let sign = /\d\d-\d\d:\d\d$/.test(prop)? '-' : '+';

            // Apply the sign
            offsetMinutes = 0 + (sign == '-'? -1 * offsetMinutes : offsetMinutes);

            // Apply offset and local timezone
            d.setMinutes(d.getMinutes() - offsetMinutes - d.getTimezoneOffset())
        }
        else
        if(prop.indexOf("Z", prop.length - 1) !== -1) {
            d = new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds(), d.getMilliseconds()));
        }

        // d is now a local time equivalent to the supplied time
        return d;
    }

    function checkFromXmlDateTimePaths(value, childName, fullPath) {
        if(config.datetimeAccessFormPaths.length > 0) {
            let path = fullPath.split("\.#")[0];
            let idx = 0;
            for(; idx < config.datetimeAccessFormPaths.length; idx++) {
                let dtPath = config.datetimeAccessFormPaths[idx];
                if( typeof dtPath === "string" ) {
                    if(dtPath == path)
                        break;
                }
                else
                if( dtPath instanceof RegExp) {
                    if(dtPath.test(path))
                        break;
                }
                else
                if( typeof dtPath === "function") {
                    if(dtPath(obj, childName, path))
                        break;
                }
            }
            if(idx!=config.datetimeAccessFormPaths.length) {
                return fromXmlDateTime(value);
            }
            else
                return value;
        }
        else
            return value;
    }

    function parseDOMChildren( node, path ) {
        if(node.nodeType == DOMNodeTypes.DOCUMENT_NODE) {
            let result = new Object;
            let nodeChildren = node.childNodes;
            // Alternative for firstElementChild which is not supported in some environments
            for(let cidx=0; cidx <nodeChildren.length; cidx++) {
                let child = nodeChildren.item(cidx);
                if(child.nodeType == DOMNodeTypes.ELEMENT_NODE) {
                    let childName = getNodeLocalName(child);
                    result[childName] = parseDOMChildren(child, childName);
                }
            }
            return result;
        }
        else
        if(node.nodeType == DOMNodeTypes.ELEMENT_NODE) {
            let result = new Object;
            result.__cnt=0;

            let nodeChildren = node.childNodes;

            // Children nodes
            for(let cidx=0; cidx <nodeChildren.length; cidx++) {
                let child = nodeChildren.item(cidx); // nodeChildren[cidx];
                let childName = getNodeLocalName(child);

                if(child.nodeType!= DOMNodeTypes.COMMENT_NODE) {
                    result.__cnt++;
                    if(result[childName] == null) {
                        result[childName] = parseDOMChildren(child, path+"."+childName);
                        toArrayAccessForm(result, childName, path+"."+childName);
                    }
                    else {
                        if(result[childName] != null) {
                            if( !(result[childName] instanceof Array)) {
                                result[childName] = [result[childName]];
                                toArrayAccessForm(result, childName, path+"."+childName);
                            }
                        }
                        (result[childName])[result[childName].length] = parseDOMChildren(child, path+"."+childName);
                    }
                }
            }

            // Attributes
            for(let aidx=0; aidx <node.attributes.length; aidx++) {
                let attr = node.attributes.item(aidx); // [aidx];
                result.__cnt++;
                result[config.attributePrefix+attr.name]=attr.value;
            }

            // Node namespace prefix
            let nodePrefix = getNodePrefix(node);
            if(nodePrefix!=null && nodePrefix!="") {
                result.__cnt++;
                result.__prefix=nodePrefix;
            }

            if(result["#text"]!=null) {
                result.__text = result["#text"];
                if(result.__text instanceof Array) {
                    result.__text = result.__text.join("\n");
                }
                if(config.escapeMode)
                    result.__text = unescapeXmlChars(result.__text);
                if(config.stripWhitespaces)
                    result.__text = result.__text.trim();
                delete result["#text"];
                if(config.arrayAccessForm=="property")
                    delete result["#text_asArray"];
                result.__text = checkFromXmlDateTimePaths(result.__text, childName, path+"."+childName);
            }
            if(result["#cdata-section"]!=null) {
                result.__cdata = result["#cdata-section"];
                delete result["#cdata-section"];
                if(config.arrayAccessForm=="property")
                    delete result["#cdata-section_asArray"];
            }

            if( result.__cnt == 1 && result.__text!=null  ) {
                result = result.__text;
            }
            else
            if( result.__cnt == 0 && config.emptyNodeForm=="text" ) {
                result = '';
            }
            else
            if ( result.__cnt > 1 && result.__text!=null && config.skipEmptyTextNodesForObj) {
                if( (config.stripWhitespaces && result.__text=="") || (result.__text.trim()=="")) {
                    delete result.__text;
                }
            }
            delete result.__cnt;

            if( config.enableToStringFunc && (result.__text!=null || result.__cdata!=null )) {
                result.toString = function() {
                    return (this.__text!=null? this.__text:'')+( this.__cdata!=null ? this.__cdata:'');
                };
            }

            return result;
        }
        else
        if(node.nodeType == DOMNodeTypes.TEXT_NODE || node.nodeType == DOMNodeTypes.CDATA_SECTION_NODE) {
            return node.nodeValue;
        }
    }

    function startTag(jsonObj, element, attrList, closed) {
        let resultStr = "<"+ ( (jsonObj!=null && jsonObj.__prefix!=null)? (jsonObj.__prefix+":"):"") + element;
        if(attrList!=null) {
            for(let aidx = 0; aidx < attrList.length; aidx++) {
                let attrName = attrList[aidx];
                let attrVal = jsonObj[attrName];
                if(config.escapeMode)
                    attrVal=escapeXmlChars(attrVal);
                resultStr+=" "+attrName.substr(config.attributePrefix.length)+"='"+attrVal+"'";
            }
        }
        if(!closed)
            resultStr+=">";
        else
            resultStr+="/>";
        return resultStr;
    }

    function endTag(jsonObj,elementName) {
        return "</"+ (jsonObj.__prefix!=null? (jsonObj.__prefix+":"):"")+elementName+">";
    }

    function endsWith(str, suffix) {
        return str.indexOf(suffix, str.length - suffix.length) !== -1;
    }

    function jsonXmlSpecialElem ( jsonObj, jsonObjField ) {
        if((config.arrayAccessForm=="property" && endsWith(jsonObjField.toString(),("_asArray")))
            || jsonObjField.toString().indexOf(config.attributePrefix)==0
            || jsonObjField.toString().indexOf("__")==0
            || (jsonObj[jsonObjField] instanceof Function) )
            return true;
        else
            return false;
    }

    function jsonXmlElemCount ( jsonObj ) {
        let elementsCnt = 0;
        if(jsonObj instanceof Object ) {
            for( let it in jsonObj  ) {
                if(jsonXmlSpecialElem ( jsonObj, it) )
                    continue;
                elementsCnt++;
            }
        }
        return elementsCnt;
    }

    function parseJSONAttributes ( jsonObj ) {
        let attrList = [];
        if(jsonObj instanceof Object ) {
            for( let ait in jsonObj  ) {
                if(ait.toString().indexOf("__")== -1 && ait.toString().indexOf(config.attributePrefix)==0) {
                    attrList.push(ait);
                }
            }
        }
        return attrList;
    }

    function parseJSONTextAttrs ( jsonTxtObj ) {
        let result ="";

        if(jsonTxtObj.__cdata!=null) {
            result+="<![CDATA["+jsonTxtObj.__cdata+"]]>";
        }

        if(jsonTxtObj.__text!=null) {
            if(config.escapeMode)
                result+=escapeXmlChars(jsonTxtObj.__text);
            else
                result+=jsonTxtObj.__text;
        }
        return result;
    }

    function parseJSONTextObject ( jsonTxtObj ) {
        let result ="";

        if( jsonTxtObj instanceof Object ) {
            result+=parseJSONTextAttrs ( jsonTxtObj );
        }
        else
        if(jsonTxtObj!=null) {
            if(config.escapeMode)
                result+=escapeXmlChars(jsonTxtObj);
            else
                result+=jsonTxtObj;
        }

        return result;
    }

    function parseJSONArray ( jsonArrRoot, jsonArrObj, attrList ) {
        let result = "";
        if(jsonArrRoot.length == 0) {
            result+=startTag(jsonArrRoot, jsonArrObj, attrList, true);
        }
        else {
            for(let arIdx = 0; arIdx < jsonArrRoot.length; arIdx++) {
                result+=startTag(jsonArrRoot[arIdx], jsonArrObj, parseJSONAttributes(jsonArrRoot[arIdx]), false);
                result+=parseJSONObject(jsonArrRoot[arIdx]);
                result+=endTag(jsonArrRoot[arIdx],jsonArrObj);
            }
        }
        return result;
    }

    function parseJSONObject ( jsonObj ) {
        let result = "";

        let elementsCnt = jsonXmlElemCount ( jsonObj );

        if(elementsCnt > 0) {
            for( let it in jsonObj ) {

                if(jsonXmlSpecialElem ( jsonObj, it) )
                    continue;

                let subObj = jsonObj[it];

                let attrList = parseJSONAttributes( subObj )

                if(subObj == null || subObj == undefined) {
                    result+=startTag(subObj, it, attrList, true);
                }
                else
                if(subObj instanceof Object) {

                    if(subObj instanceof Array) {
                        result+=parseJSONArray( subObj, it, attrList );
                    }
                    else if(subObj instanceof Date) {
                        result+=startTag(subObj, it, attrList, false);
                        result+=subObj.toISOString();
                        result+=endTag(subObj,it);
                    }
                    else {
                        let subObjElementsCnt = jsonXmlElemCount ( subObj );
                        if(subObjElementsCnt > 0 || subObj.__text!=null || subObj.__cdata!=null) {
                            result+=startTag(subObj, it, attrList, false);
                            result+=parseJSONObject(subObj);
                            result+=endTag(subObj,it);
                        }
                        else {
                            result+=startTag(subObj, it, attrList, true);
                        }
                    }
                }
                else {
                    result+=startTag(subObj, it, attrList, false);
                    result+=parseJSONTextObject(subObj);
                    result+=endTag(subObj,it);
                }
            }
        }
        result+=parseJSONTextObject(jsonObj);

        return result;
    }

    this.parseXmlString = function(xmlDocStr) {
        if (xmlDocStr === undefined) {
            return null;
        }
        let xmlDoc;
        let isIEVer = MUtilComm_GetIEVersion();

        if (window.DOMParser && isIEVer == "0") {
            let parser=new window.DOMParser();
            let parsererrorNS = parser.parseFromString('INVALID', 'text/xml').childNodes[0].namespaceURI;
            xmlDoc = parser.parseFromString( xmlDocStr, "text/xml" );
            if(xmlDoc.getElementsByTagNameNS(parsererrorNS, 'parsererror').length > 0) {
                //throw new Error('Error parsing XML: '+xmlDocStr);
                xmlDoc = null;
            }
        }
        else {
            // IE :(
            if(xmlDocStr.indexOf("<?")==0) {
                xmlDocStr = xmlDocStr.substr( xmlDocStr.indexOf("?>") + 2 );
            }
            xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
            xmlDoc.async="false";
            xmlDoc.loadXML(xmlDocStr);
        }
        return xmlDoc;
    };

    this.asArray = function(prop) {
        if(prop instanceof Array)
            return prop;
        else
            return [prop];
    };

    this.toXmlDateTime = function(dt) {
        if(dt instanceof Date)
            return dt.toISOString();
        else
        if(typeof(dt) === 'number' )
            return new Date(dt).toISOString();
        else
            return null;
    };

    this.asDateTime = function(prop) {
        if(typeof(prop) == "string") {
            return fromXmlDateTime(prop);
        }
        else
            return prop;
    };

    this.xml2json = function (xmlDoc) {
        return parseDOMChildren ( xmlDoc );
    };

    this.xml_str2json = function (xmlDocStr) {
        let xmlDoc = this.parseXmlString(xmlDocStr);
        if(xmlDoc!=null)
            return this.xml2json(xmlDoc);
        else
            return null;
    };

    this.json2xml_str = function (jsonObj) {
        return parseJSONObject ( jsonObj );
    };

    this.json2xml = function (jsonObj) {
        let xmlDocStr = this.json2xml_str (jsonObj);
        return this.parseXmlString(xmlDocStr);
    };

    this.getVersion = function () {
        return VERSION;
    };

}

function ajaxReq(url, method, cb, data, loadingFlag, isAsync){
    if(isAsync == undefined) {
        isAsync = true;
    }

    if(loadingFlag == undefined || loadingFlag == null){
        loadingFlag = true;
    }

    if(url.indexOf('?') > -1){
        url = url + '&reqTime=' + new Date().getTime();
    }else{
        url = url + '?reqTime=' + new Date().getTime();
    }

    $.ajax({
        type: method,
        async: isAsync,
        contentType : 'application/json;charset=UTF-8',
        url: url,
        cashe: false,
        dataType:'json',
        beforeSend: function() {
            if(data){
                this.data = JSON.stringify(data);
            }
//			if(formid != null && $('#'+formid) != null){
//				this.data = $('#'+formid).serialize();
//			}

            if(loadingFlag){
                loaderOpen();
            }
        },
        complete: function() {
            if(loadingFlag){
                loaderClose();
            }
        },
        success: function(data) {
//			object = JSON.parse(data);
            object = data;
            try{
                cb(object);
            }catch(e){
                console.log("[error tr data : "+e.message+"] 데이터 처리도중 문제가 발생했습니다.");
                console.log("[Error Stack]\n"+ e.stack);
            }
        },
        error: function(data, status, err) {
            console.log("error forward : [data]", data);
            console.log("[status]", status);
            console.log("[err]", err);

            if(loadingFlag){
                loaderClose();
            }

            if(data.status == '404'){
                if(cb){
                    let obj = {
                        responceCode : 1,
                        message : '404 오류'
                    };
                    cb(obj);
                }
            }else if(data.status == '444'){
                location.href = '/sessionExpire';
            }else{
                if(cb){
                    let obj = {
                        responceCode : status,
                        message : '['+ status +'] 오류'
                    };
                    cb(obj);
                }
            }
        }
    });
}



// $(window).bind('pageshow', function(event){
// 	if(event.originalEvent.persisted){
// 		setTimeout(function(){
// 			$.unblockUI();
// 			location.reload();
// 		},500);
// 	}
// });

let loaderCnt = 0;
function loaderOpen() {
    if(loaderCnt == 0) {
        // let opts = {
        // 		lines: 13, // The number of lines to draw
        // 		length: 16, // The length of each line
        // 		width: 10, // The line thickness
        // 		radius: 26, // The radius of the inner circle
        // 		corners: 1, // Corner roundness (0..1)
        // 		rotate: 0, // The rotation offset
        // 		direction: 1, // 1: clockwise, -1: counterclockwise
        // 		color: '#000', // #rgb or #rrggbb or array of colors
        // 		speed: 1, // Rounds per second
        // 		trail: 60, // Afterglow percentage
        // 		shadow: false, // Whether to render a shadow
        // 		hwaccel: false, // Whether to use hardware acceleration
        // 		className: 'spinner', // The CSS class to assign to the spinner
        // 		zIndex: 2e9, // The z-index (defaults to 2000000000)
        // 		top: '50%', // Top position relative to parent
        // 		left: '50%' // Left position relative to parent
        // };
        //
        // let spinner = new Spinner(opts).spin();
        // //spinner.el
        // $.blockUI({
        // 	fadeIn : 0,
        // 	fadeOut : 0,
        // 	//showOverlay : false,
        // 	message: spinner.el,
        // 	css: { border: '0px' }
        // });

        $.blockUI({
            fadeIn : 0,
            fadeOut : 0,
            message: "<div id='loadingPopup'></div>",
            css: { border: '0px',
                backgroundColor:'rgba(0,0,0,0.0)' ,
                width : '40%',
                left: '30%',
            }
        });

        lottie.loadAnimation({
            container: document.getElementById('loadingPopup'),
            renderer: 'svg',
            loop: true,
            autoplay: true,
            path: '/images/animation/logo_loading_20221223_2.json'
        });

    }
    loaderCnt++;
    // if(debugOption.dev()){
    // 	consoleML.log('loaderCnt+ : ', loaderCnt);
    // }
}

function loaderClose() {
    --loaderCnt;
    // if(debugOption.dev()){
    // 	consoleML.log('loaderCnt- : ', loaderCnt, '#32c0aa');
    // }

    if(loaderCnt <= 0){
        loaderCnt = 0;
        $.unblockUI();
    }
}


function ajaxReqWithIFrameResponse(id, iframe_id){
    if(id) {
        $('#' + id + ' > div:last').remove();
        $('#'+id).css('position', this.c_position);
    }
}

function ajaxReqWithIFrame(url, cb, id, formid){
    let form = document.getElementById(formid);
    let iframe_id = id+formid+'_iframe';

    this.c_position = $('#'+id).css('position');
    $('#'+id).css('position', 'relative');
    $('#'+id).append('<div style="width:100%;height:100%;background:#000;opacity:0.5;text-align:center;color:white;font-weight:bold;float:left;position:absolute;left:0px;top:0px;"><table style="width: 100%; height: 100%"><tr><td><div>Loading...</div></td></tr></table></div>');
    $('#'+id).append('<iframe style="visibility:hidden;" id="'+iframe_id+'"></iframe>');

    form.setAttribute('action', url);
    form.setAttribute('enctype', 'multipart/form-data');
    form.setAttribute('method', 'POST');
    form.setAttribute('target', iframe_id);

    form.submit();

    setTimeout(ajaxReqWithIFrameResponse(id, iframe_id), 2000);
}


function ajaxReqWithFile(url, cb, formid, loadingFlag){
    let formData = new FormData();

    if(loadingFlag == undefined || loadingFlag == null){
        loadingFlag = true;
    }

    if(formData != null){
        $.ajax({
            type: "POST",
            async: true,
            processData: false,
            contentType: false,
            url: url,
            dataType:'html',
            beforeSend: function() {
                if(formid != null){
                    let form = $(formid)[0];
                    let formData = new FormData(form);
                    this.data = formData;

                    if(loadingFlag){
                        loaderOpen();
                    }
                }
            },
            complete: function() {
                if(loadingFlag){
                    loaderClose();
                }
            },
            success: function(data) {
                let object = null;
                object = JSON.parse(data);
                cb(object);
            },
            error: function(data, status, err) {
                console.log("error forward : "+data);
                if(data.status == '404'){
                    if(cb){
                        let obj = {
                            responseCode : 3000,
                            message : '첨부파일을 업로드 하지 못했습니다. 첨부파일을 확인 해주세요.'
                        };
                        cb(obj);
                    }
                }else if(data.status == '444'){
                    alert('로그인 세션이 만료 되었습니다.');
                    location.href = '/sessionExpire';
                }
            }
        });
    } else {
        doto_nextwork();
    }

}


function ajaxReqWithFormFile(url, cb, formdata, loadingFlag){
    let formData = new FormData();

    if(loadingFlag == undefined || loadingFlag == null){
        loadingFlag = true;
    }

    if(formData != null){
        $.ajax({
            type: "POST",
            async: true,
            processData: false,
            contentType: false,
            url: url,
            dataType:'html',
            beforeSend: function() {
                if(formdata != null){
                    this.data = formdata;

                    if(loadingFlag){
                        loaderOpen();
                    }
                }
            },
            complete: function() {
                if(loadingFlag){
                    loaderClose();
                }
            },
            success: function(data) {
                let object = null;
                object = JSON.parse(data);
                cb(object);
            },
            error: function(data, status, err) {
                console.log("error forward : ", data);
                if(data.status == '404'){
                    if(cb){
                        let obj = {
                            responseCode : 3000,
                            message : '첨부파일을 업로드 하지 못했습니다. 파일을 확인 해주세요.'
                        };
                        cb(obj);
                    }
                }else if(data.status == '444'){
                    alert('로그인 세션이 만료 되었습니다.');
                    location.href = '/sessionExpire';
                }
            }
        });
    } else {
        doto_nextwork();
    }

}