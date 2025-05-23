export const setPageWide = function(lastPage, pageNo) {
    let pageWide = [1]; // 초기값 세팅

    if (10 < lastPage) {
        let offset = Math.trunc((pageNo - 1) / 10);
        for (let i = 0; i < 10; i++) {
            let p = offset * 10 + i + 1;
        
            if (this.lastPage < p) {
                pageWide.splice(i);
                break;
            }
            pageWide[i] = p;
        }
    } else if (1 < lastPage) {
        for (let i = 0; i < lastPage; i++) {
            pageWide[i] = i + 1;
        }
    }
    
