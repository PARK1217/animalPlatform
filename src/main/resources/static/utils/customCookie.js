/**
 *
 */

/**
 * Create by        : LarryPark
 * @param key		: Key명칭
 * @param val		: 값
 * @param expireTime	: 쿠키 만료일 time설정
 * @param path		: 쿠기 path 설정 (Default : /)
 * @returns
 * Description    : 웹 쿠키 등록
 */
function MLSetCookie(key, val, expireTime, path){
    if(key && val
        && (typeof(expireTime) == 'number' ? ('' + expireTime).length : expireTime.length) > 0){

        if(path){
            document.cookie = key + '='+ val +';Expires=' + new Date(new Date().getTime() + expireTime).toUTCString() + ';path=' + path;
        }else{
            document.cookie = key + '='+ val +';Expires=' + new Date(new Date().getTime() + expireTime).toUTCString() + ';path=/';
        }
        return document.cookie;
    }
    return null;
}

function MLGetCookie(key){
    let cookie = document.cookie;
    if(cookie){
        if(key){
            let splitData = cookie.split(';');
            for(i in splitData){
                let keyVal = splitData[i].split('=');
                if(keyVal[0].trim() == key){
                    return keyVal[1];
                }
            }
        }else{
            return cookie;
        }
    }
    return null;
}

function MLRemoveCookie(key, path){
    if(key){
        let currentDate = new Date();
        currentDate.setDate(currentDate.getDate()-1);

        if(path){
            document.cookie = key + '=dummy;Expires=' + currentDate.toUTCString() + ';path=' + path;
        }else{
            document.cookie = key + '=dummy;Expires=' + currentDate.toUTCString() + ';path=/';
        }
        return document.cookie;
    }
}

