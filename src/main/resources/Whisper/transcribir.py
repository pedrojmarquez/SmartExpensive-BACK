import sys
import os
import warnings
import contextlib
import pkg_resources
from faster_whisper import WhisperModel

# Silenciar advertencias
warnings.filterwarnings("ignore")
os.environ["HF_HUB_DISABLE_SYMLINKS_WARNING"] = "1"

# Redirigir stderr (donde se imprimen los warnings de ctranslate2)
with open(os.devnull, 'w') as devnull, contextlib.redirect_stderr(devnull):
    # Obtener el audio desde argumento
    audio_path = sys.argv[1]

    # Cargar modelo rápido y ligero
    model = WhisperModel("tiny", device="cpu", compute_type="int8")

    # Transcribir
    segments, _ = model.transcribe(audio_path)
    texto = " ".join(segment.text.strip() for segment in segments)

# Imprimir solo el texto (sin logs ni advertencias)
print(texto.strip())


