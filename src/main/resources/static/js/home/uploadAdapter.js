// class UploadAdapter {
//     constructor(loader) {
//         this.loader = loader;
//     }
//
//     upload() {
//         return this.loader.file.then( file => new Promise(((resolve, reject) => {
//             this._initRequest();
//             this._initListeners( resolve, reject, file );
//             this._sendRequest( file );
//         })))
//     }
//
//     _initRequest() {
//         const xhr = this.xhr = new XMLHttpRequest();
//         xhr.open('POST', '/ajax/home/upload/image', true);
//         xhr.responseType = 'json';
//     }
//
//     _initListeners(resolve, reject, file) {
//         const xhr = this.xhr;
//         const loader = this.loader;
//         const genericErrorText = '파일을 업로드 할 수 없습니다.'
//
//         xhr.addEventListener('error', () => {reject(genericErrorText)})
//         xhr.addEventListener('abort', () => reject())
//         xhr.addEventListener('load', () => {
//             const response = xhr.response
//             if(!response || response.error) {
//                 return reject( response && response.error ? response.error.message : genericErrorText );
//             }
//
//             resolve({
//                 default: response.url //업로드된 파일 주소
//             })
//         })
//     }
//
//     _sendRequest(file) {
//         const data = new FormData()
//         data.append('upload',file)
//         this.xhr.send(data)
//     }
// }

class MyUploadAdapter {
    constructor( loader ) {
        // CKEditor 5's FileLoader instance.
        this.loader = loader;

        // URL where to send files.
        this.url = 'http://localhost:8090/home/photo/write';
    }

    // Starts the upload process.
    upload() {
        return new Promise( ( resolve, reject ) => {
            this._initRequest();
            this._initListeners( resolve, reject );
            this._sendRequest();
        } );
    }

    // Aborts the upload process.
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // Example implementation using XMLHttpRequest.
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        xhr.open( 'POST', this.url, true );
        xhr.responseType = 'json';
    }

    // Initializes XMLHttpRequest listeners.
    _initListeners( resolve, reject ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = 'Couldn\'t upload file:' + ` ${ loader.file.name }.`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;

            if ( !response || response.error ) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            // If the upload is successful, resolve the upload promise with an object containing
            // at least the "default" URL, pointing to the image on the server.
            resolve( {
                default: response.url
            } );
        } );

        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    // Prepares the data and sends the request.
    _sendRequest() {
        const data = new FormData();

        data.append( 'upload', this.loader.file );

        this.xhr.send( data );
    }
}